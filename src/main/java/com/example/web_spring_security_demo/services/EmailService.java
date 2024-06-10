package com.example.web_spring_security_demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") private String sender;
    public String sendMail(String emailTo, String content, String subject)
    {
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailTo);
            mailMessage.setText(content);
            mailMessage.setSubject(subject);
            javaMailSender.send(mailMessage);
            return "Gửi email thành công";
        }catch (Exception e)
        {
            return "Email ko tồn tại";
        }
    }
}
