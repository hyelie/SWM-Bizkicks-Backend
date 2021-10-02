package com.bizkicks.backend.auth.controller;

import com.bizkicks.backend.auth.dto.EmailDto;
import com.bizkicks.backend.auth.dto.MemberDto;
import com.bizkicks.backend.auth.dto.PasswordDto;
import com.bizkicks.backend.auth.dto.TokenDto;
import com.bizkicks.backend.auth.entity.Member;
import com.bizkicks.backend.auth.service.AuthService;
import com.bizkicks.backend.auth.service.EmailService;
import com.bizkicks.backend.auth.service.MemberService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    @Autowired private AuthService authService;
    @Autowired private MemberService memberService;
    @Autowired private EmailService emailService;

    @PostMapping("/member/signup")
    public ResponseEntity<Object> signup(@RequestBody MemberDto memberDto){
        Member member = authService.signup(memberDto);
        authService.sendVerificationEmail(member);

        JSONObject returnObject = new JSONObject();
        returnObject.put("msg", "Success");
        log.info("새로운 회원가입");
        return new ResponseEntity<Object>(returnObject.toString(), HttpStatus.CREATED);
    }

    @PostMapping("/member/login")
    public ResponseEntity<Object> login(@RequestBody MemberDto memberDto){
        return new ResponseEntity<Object>(authService.login(memberDto), HttpStatus.OK);
    }

    @PostMapping("/member/reissue")
    public ResponseEntity<Object> reissue(@RequestBody TokenDto tokenDto){
        return new ResponseEntity<Object>(authService.reissue(tokenDto), HttpStatus.OK);
    }

    @GetMapping("/member/me")
    public ResponseEntity<Object> currentMemberInfo(){
        
        Member member = memberService.getCurrentMemberInfo();
        MemberDto memberDto = MemberDto.builder()
                                        .id(member.getMemberId())
                                        .name(member.getName())
                                        .license(member.getLicense())
                                        .user_role(member.getUserRole())
                                        .email(member.getEmail())
                                        .company_name(member.getCustomerCompany().getCompanyName())
                                        .build();
                                        
        return new ResponseEntity<Object>(memberDto, HttpStatus.OK);
    }

    @PostMapping(value="/member/find/id")
    public ResponseEntity<Object> findMemberId(@RequestBody EmailDto emailDto) {
        String email = emailDto.getEmail();
        authService.sendIdEmail(email);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "Success");
        log.info("아이디 찾기 요청 : " + email);

        return new ResponseEntity<Object>(jsonObject.toString(), HttpStatus.OK);
    }

    @PostMapping(value="/member/find/password")
    public ResponseEntity<Object> findMemberPassword(@RequestBody EmailDto emailDto) {
        authService.reissuePassword(emailDto.getEmail());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "Success");
        log.info("비밀번호 찾기 요청 : " + emailDto.getEmail());

        return new ResponseEntity<Object>(jsonObject.toString(), HttpStatus.OK);
    }

    @PostMapping(value="/member/modify/password")
    public ResponseEntity<Object> changeMemberPassword(@RequestBody PasswordDto passwordDto) {
        Member member = memberService.getCurrentMemberInfo();
        authService.changeMemberPassword(member, passwordDto.getOld_password(), passwordDto.getNew_password());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "Success");
        log.info("사용자 {} 비밀번호 수정 요청", member.getMemberId());

        return new ResponseEntity<Object>(jsonObject.toString(), HttpStatus.OK);
    }

    @GetMapping(value = "/member/verify/{key}")
    public ResponseEntity<Object> verifyUserWIthKey(@PathVariable(value = "key", required = true) String key){
     
        authService.verifyEmail(key);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "Success");
        log.info("이메일 인증 완료");

        return new ResponseEntity<Object>(jsonObject.toString(), HttpStatus.OK);
    }

}
