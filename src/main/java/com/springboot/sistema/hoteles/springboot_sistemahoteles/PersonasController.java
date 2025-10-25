package com.springboot.sistema.hoteles.springboot_sistemahoteles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class PersonasController {
    @Autowired
    @RequestMapping("/personas")
    public String page() {
        //model.addAttribute("attribute", "value");
        return "personas";
    }
}
