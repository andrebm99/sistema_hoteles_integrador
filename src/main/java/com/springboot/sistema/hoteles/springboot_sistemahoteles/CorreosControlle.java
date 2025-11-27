package com.springboot.sistema.hoteles.springboot_sistemahoteles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Email;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.services.EmailService;

// @Controller
public class CorreosControlle {
    @Autowired
    private JavaMailSender emailSender;
    @RequestMapping("/correos")
    public String page() {
        EmailService.SolicitarEnvio(
                new Email("jcervanteslivon@gmail.com","Mensaje de prueba integrador","Mensaje de prueba"),
                emailSender);
        EmailService.SolicitarEnvio(
                new Email("u22217197@utp.edu.pe","Mensaje de prueba integrador","Mensaje de prueba"),
                emailSender);
        return "correos";
    }
}