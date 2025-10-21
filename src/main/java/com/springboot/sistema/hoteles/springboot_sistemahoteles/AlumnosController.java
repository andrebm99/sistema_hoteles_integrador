package com.springboot.sistema.hoteles.springboot_sistemahoteles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AlumnosController {
    @Autowired
    @RequestMapping("/alumnos")
    public String page() {
        return "alumnos";
    }
}
