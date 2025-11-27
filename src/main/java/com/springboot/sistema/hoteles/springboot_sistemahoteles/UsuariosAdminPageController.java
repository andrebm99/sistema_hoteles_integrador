package com.springboot.sistema.hoteles.springboot_sistemahoteles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuariosAdminPageController {
    @GetMapping("/usuarios_admin")
    public String page() {
        return "usuarios_admin";
    }
}