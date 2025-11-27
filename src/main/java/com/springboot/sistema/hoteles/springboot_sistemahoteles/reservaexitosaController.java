package com.springboot.sistema.hoteles.springboot_sistemahoteles;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservaExitosaController {

    @GetMapping("/reserva_exitosa")
    public String reservaExitosa(@RequestParam(name="codigo", required=false) String codigo, Model model) {
        model.addAttribute("codigoReserva", codigo != null ? codigo : "DESCONOCIDO");
        return "reserva_exitosa";
    }
}