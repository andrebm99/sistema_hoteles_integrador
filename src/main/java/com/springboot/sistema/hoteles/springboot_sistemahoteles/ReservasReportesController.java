package com.springboot.sistema.hoteles.springboot_sistemahoteles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservasReportesController {
    @GetMapping("/reservas/reportes")
    public String page() {
        return ("rutas_admin/reservas_reportes");
    }
    
}
