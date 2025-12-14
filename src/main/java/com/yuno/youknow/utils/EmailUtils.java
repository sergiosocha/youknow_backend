package com.yuno.youknow.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailUtils {

    @Autowired
    private JavaMailSender mailSender;
    public  void sendEmail(String subject, String body, String email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("help.tickets.sabanus@gmail.com");
            message.setTo(email); // destinatario real
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
