package com.bizkicks.backend.auth.service;

import java.util.UUID;

import javax.transaction.Transactional;

import com.bizkicks.backend.auth.dto.EmailDto;
import com.bizkicks.backend.auth.dto.MemberDto;
import com.bizkicks.backend.auth.dto.TokenDto;
import com.bizkicks.backend.auth.entity.Member;
import com.bizkicks.backend.auth.entity.UserRole;
import com.bizkicks.backend.auth.redis.RedisUtil;
import com.bizkicks.backend.auth.repository.MemberRepository;
import com.bizkicks.backend.auth.security.jwt.JwtUtil;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.repository.CustomerCompanyRepository;
import com.bizkicks.backend.util.GetWithNullCheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired private MemberRepository memberRepository;
    @Autowired private CustomerCompanyRepository customerCompanyRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    @Autowired private GetWithNullCheck getWithNullCheck;
    @Autowired private RedisUtil redisUtil;
    @Autowired EmailService emailService;

    @Value("${mail.verification.link}")
    private String VERIFICATION_LINK;

    public static final Long EmailExpireTime = 1000 * 60 * 30L; // 30분


    @Transactional
    public Member signup(MemberDto memberDto){
        
        CustomerCompany customerCompany = getWithNullCheck.getCustomerCompanyWithCode(customerCompanyRepository, memberDto.getCompany_code());
        if(memberDto.getId() == null || memberDto.getPassword() == null || memberDto.getCompany_code() == null || customerCompany == null){
            throw new CustomException(ErrorCode.PARAMETER_NOT_VALID);
        }
        if(memberRepository.existsByMemberId(memberDto.getId())){
            throw new CustomException(ErrorCode.ID_DUPLICATED);
        }
        Member member = memberDto.toEntity(passwordEncoder);
        member.setUserROle(UserRole.ROLE_NOT_PERMITTED);
        member.setRelationWithCustomerCompany(customerCompany);

        memberRepository.save(member);

        return member;
    }

    @Transactional
    public TokenDto login(MemberDto memberDto){
        UsernamePasswordAuthenticationToken authenticationToken = memberDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = jwtUtil.generateTokenDto(authentication);

        redisUtil.set(authentication.getName(), tokenDto.getRefreshToken(), jwtUtil.getRefreshTokenExpireTime());
        
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenDto tokenDto){
        if(!jwtUtil.validateToken(tokenDto.getRefreshToken())){
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        Authentication authentication = jwtUtil.getAuthentication(tokenDto.getAccessToken());

        String refreshToken = redisUtil.get(authentication.getName());
        if(refreshToken == null){
            new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        }

        if(!refreshToken.equals(tokenDto.getRefreshToken())){
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        TokenDto returnTokenDto = jwtUtil.generateTokenDto(authentication);

        redisUtil.set(authentication.getName(), returnTokenDto.getRefreshToken(), jwtUtil.getRefreshTokenExpireTime());

        return returnTokenDto;
    }

    public void sendIdEmail(String email){
        Member member = getWithNullCheck.getMemberByEmail(memberRepository, email);

        String text = emailService.idText(member.getMemberId(), member.getName());
        String title = emailService.idTitle();
        emailService.sendMail(member.getEmail(), title, text);
    }

    public void changeMemberPassword(Member member, String oldPassword, String newPassword){
        if(!this.passwordEncoder.matches(oldPassword, member.getPassword())){
            throw new CustomException(ErrorCode.PASSWORD_NOT_VALID);
        }
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

    public void reissuePassword(String email){
        Member member = getWithNullCheck.getMemberByEmail(memberRepository, email);
        
        String tempPassword = "";
        for (int i = 0; i < 12; i++) {
			tempPassword += (char) ((Math.random() * 26) + 97);
		}

        member.setPassword(passwordEncoder.encode(tempPassword));
        memberRepository.save(member);

        String text = emailService.tempPasswordText(member.getName(), tempPassword);
        String title = emailService.tempPasswordTItle();
        emailService.sendMail(member.getEmail(), title, text);
    }

    public void sendVerificationEmail(Member member){
        UUID uuid = UUID.randomUUID();
        redisUtil.set(uuid.toString(), member.getMemberId(), EmailExpireTime);

        String link = this.VERIFICATION_LINK + uuid.toString();
        String title = emailService.verifyEmailTitle();
        String text = emailService.verifyEmailText(link);
        emailService.sendMail(member.getEmail(), title, text);
    }

    public void verifyEmail(String key){
        if(!redisUtil.hasKey(key)){
            throw new CustomException(ErrorCode.LINK_NOT_EXIST);
        }

        String memberId = redisUtil.get(key);
        Member member = getWithNullCheck.getMemberByMemberId(memberRepository, memberId);
        modifyUserRole(member, UserRole.ROLE_USER);
    }

    public void modifyUserRole(Member member, UserRole userRole){
        member.setUserROle(userRole);
        memberRepository.save(member);
        return;
    }

    
}
