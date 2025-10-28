package com.springboot.sistema.hoteles.springboot_sistemahoteles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
public class loginsController {
    @Autowired
    @RequestMapping("/login")
    public String page() {
        return "login";
    }
}