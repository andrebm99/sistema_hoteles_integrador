package com.springboot.sistema.hoteles.springboot_sistemahoteles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Email;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.services.EmailService;


/**
 *
 * @author jcerv
 */
@Controller
public class CorreosController {
    @Autowired
    private JavaMailSender emailSender;
    @RequestMapping("/correos")
    public String page() {
        EmailService.SolicitarEnvio(
                new Email("ronaldobayona65@gmail.com","Sistema de Hoteles","El sistema se está ejecutando ahora."),
                emailSender);
        EmailService.SolicitarEnvio(
                new Email("u22246063@utp.edu.pe","Sistema de Hoteles","El sistema se está ejecutando ahora."),
                emailSender);
        return "correos";
    }
}
