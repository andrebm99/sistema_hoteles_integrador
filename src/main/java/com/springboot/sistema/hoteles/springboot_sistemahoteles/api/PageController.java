package com.springboot.sistema.hoteles.springboot_sistemahoteles.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

  // Opción A: si tu landing es un TEMPLATE (templates/home.html)
  @GetMapping({"/", "/home"})
  public String home() { return "home"; }

  @GetMapping("/login")
  public String login() { return "login"; }

  @GetMapping("/register")
  public String register() { return "register"; }

  @GetMapping("/alumnos")
  public String alumnos() { return "alumnos"; }

  @GetMapping("/profesores")
  public String profesores() { return "profesores"; }

  @GetMapping("/personas")
  public String personas() { return "personas"; }

  @GetMapping("/correos")
  public String correos() { return "correos"; }

  @GetMapping("/proceso-pago")
  public String procesoPago() { return "proceso_pago"; }

  @GetMapping("/ajustescuenta")
  public String ajustescuentas() { return "ajustescuenta"; }
}
