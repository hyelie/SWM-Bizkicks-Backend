package com.bizkicks.backend.auth.service;

import javax.transaction.Transactional;

import com.bizkicks.backend.auth.dto.MemberDto;
import com.bizkicks.backend.auth.dto.TokenDto;
import com.bizkicks.backend.auth.entity.Member;
import com.bizkicks.backend.auth.redis.RedisUtil;
import com.bizkicks.backend.auth.repository.MemberRepository;
import com.bizkicks.backend.auth.security.jwt.JwtUtil;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.repository.CustomerCompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired private final RedisUtil redisUtil;

    @Transactional
    public void signup(MemberDto memberDto){
        CustomerCompany customerCompany = customerCompanyRepository.findByCustomerCompanyCode(memberDto.getCompany_code());
        if(memberDto.getId() == null || memberDto.getPassword() == null || memberDto.getCompany_code() == null || customerCompany == null){
            throw new CustomException(ErrorCode.PARAMETER_NOT_VALID);
        }
        if(memberRepository.existsByMemberId(memberDto.getId())){
            throw new CustomException(ErrorCode.ID_DUPLICATED);
        }
        Member member = memberDto.toEntity(passwordEncoder);
        
        member.setRelationWithCustomerCompany(customerCompany);

        memberRepository.save(member);
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
}
