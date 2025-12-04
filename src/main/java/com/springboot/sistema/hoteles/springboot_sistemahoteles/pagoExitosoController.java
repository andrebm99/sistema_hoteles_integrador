package com.springboot.sistema.hoteles.springboot_sistemahoteles;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Reserva;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Habitacion;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.ReservaRepository;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.HabitacionRepository;

@Controller
public class pagoExitosoController {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    @GetMapping("/pago_exitoso")
    public String pagoExitoso(@RequestParam(name = "id", required = false) Long id,
                              @RequestParam(name = "codigo", required = false) String codigo,
                              Model model) {

        Reserva reserva = null;
        if (id != null) {
            Optional<Reserva> opt = reservaRepository.findById(id);
            if (opt.isPresent()) reserva = opt.get();
        }

        if (reserva == null && codigo != null) {
            Optional<Reserva> opt2 = reservaRepository.findByCodigo(codigo);
            if (opt2.isPresent()) reserva = opt2.get();
        }

        if (reserva != null) {
            model.addAttribute("codigoReserva", reserva.getCodigo());
            Long idHab = reserva.getId_habitacion();
            if (idHab != null) {
                Optional<Habitacion> hopt = habitacionRepository.findById(idHab);
                if (hopt.isPresent()) {
                    model.addAttribute("numeroHabitacion", hopt.get().getNumerohabitacion());
                } else {
                    model.addAttribute("numeroHabitacion", "-");
                }
            } else {
                model.addAttribute("numeroHabitacion", "-");
            }
        } else {
            model.addAttribute("codigoReserva", codigo != null ? codigo : "DESCONOCIDO");
            model.addAttribute("numeroHabitacion", "-");
        }

        return "pago_exitoso";
    }
}
