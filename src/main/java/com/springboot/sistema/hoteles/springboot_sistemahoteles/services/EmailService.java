package com.springboot.sistema.hoteles.springboot_sistemahoteles.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Email;

public class EmailService {
    public static void SolicitarEnvio(Email email, JavaMailSender emailSender){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jcervanteslivon@gmail.com");
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getMessage());
        emailSender.send(message);
    }
}
