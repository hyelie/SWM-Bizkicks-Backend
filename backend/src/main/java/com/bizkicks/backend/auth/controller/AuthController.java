package com.bizkicks.backend.auth.controller;

import com.bizkicks.backend.auth.dto.MemberDto;
import com.bizkicks.backend.auth.dto.TokenDto;
import com.bizkicks.backend.auth.entity.Member;
import com.bizkicks.backend.auth.service.AuthService;
import com.bizkicks.backend.auth.service.MemberService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
    @Autowired private AuthService authService;
    @Autowired private MemberService memberService;

    @PostMapping("/member/signup")
    public ResponseEntity<Object> signup(@RequestBody MemberDto memberDto){
        authService.signup(memberDto);

        JSONObject returnObject = new JSONObject();
        returnObject.put("msg", "Success");
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
                                        .memberId(member.getMemberId())
                                        .name(member.getName())
                                        .license(member.getLicense())
                                        .userRole(member.getUserRole())
                                        .phoneNumber(member.getPhoneNumber())
                                        .customerCompanyName(member.getCustomerCompany().getCompanyName())
                                        .build();
                                        
        return new ResponseEntity<Object>(memberDto, HttpStatus.OK);
    }

}
