package com.bizkicks.backend.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService{
    @Autowired
    private JavaMailSenderImpl mailSender;
    
    public void sendMail(String to, String title, String text){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(text);
        mailSender.send(simpleMailMessage);
    }

    public String idTitle(){
        return "비즈킥스 아이디 찾기";
    }

    public String idText(String memberId, String name){
        String msg = "";
		msg += name + "님의 아아디는 다음과 같습니다.\n";
		msg += "아이디 : " + memberId;
        return msg;
    }

    public String tempPasswordTItle(){
        return "비즈킥스 임시 비밀번호";
    }

    public String tempPasswordText(String name, String tempPassword){
        String msg = "";
		msg += name + "님의 임시 비밀번호입니다. 비밀번호를 변경하여 사용하세요.\n";
		msg += "임시 비밀번호 : " + tempPassword;
        return msg;
    }

    public String verifyEmailTitle(){
        return "비즈킥스 인증 메일";
    }

    public String verifyEmailText(String link){
        String msg = "";
		msg += "접속 링크 : " + link;
        return msg;
    }
}

