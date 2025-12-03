package com.springboot.sistema.hoteles.springboot_sistemahoteles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.services.HabitacionService;

@Controller
public class ReservasUsuarioController {

    @Autowired
    private HabitacionService habitacionService;

    @GetMapping("/reservas_usuario")
    public String page(Model model) {
        model.addAttribute("habitaciones", habitacionService.findAll());
        return "reservas_usuario";
    }

}
