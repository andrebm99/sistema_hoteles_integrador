package com.springboot.sistema.hoteles.springboot_sistemahoteles;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ReservasUsuarioController {

    @GetMapping("/reservas_usuario")
    public String page() {
        return "reservas_usuario";
    }
}