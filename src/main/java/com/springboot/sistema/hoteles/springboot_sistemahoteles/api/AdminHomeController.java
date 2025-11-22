package com.springboot.sistema.hoteles.springboot_sistemahoteles.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {

    @GetMapping("/home")
    public String adminHomePage() {
        return "/rutas_admin/habitaciones";
    }
}
