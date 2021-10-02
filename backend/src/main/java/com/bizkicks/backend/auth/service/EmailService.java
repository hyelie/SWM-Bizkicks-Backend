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

    public String passwordTItle(){
        return "비즈킥스 임시 비밀번호";
    }

    public String idText(String memberId, String name){
        String msg = "";
        msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
		msg += "<h3 style='color: blue;'>";
		msg += name + "님의 아아디는 다음과 같습니다.</h3>";
		msg += "<p>아이디 : ";
		msg += memberId + "</p></div>";
        return msg;
    }

    public String tempPasswordText(String name, String tempPassword){
        String msg = "";
        msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
		msg += "<h3 style='color: blue;'>";
		msg += name + "님의 임시 비밀번호 입니다. 비밀번호를 변경하여 사용하세요.</h3>";
		msg += "<p>임시 비밀번호 : ";
		msg += tempPassword+ "</p></div>";
        return msg;
    }
}

