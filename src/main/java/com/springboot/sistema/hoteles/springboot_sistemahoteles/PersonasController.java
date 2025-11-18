package com.springboot.sistema.hoteles.springboot_sistemahoteles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonasController {

    @GetMapping("/personas")
    public String page() {
        return "personas";
    }
}
