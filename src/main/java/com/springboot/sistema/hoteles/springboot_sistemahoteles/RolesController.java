package com.springboot.sistema.hoteles.springboot_sistemahoteles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RolesController {
    @Autowired
    @GetMapping("/roles")
    public String page() {
        return "roles";
    }
    
}
