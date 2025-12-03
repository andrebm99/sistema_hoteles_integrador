package com.springboot.sistema.hoteles.springboot_sistemahoteles.api;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.services.AuthService;

@Controller
public class registerController {

    @Autowired
    private AuthService authService;

    @GetMapping("/register")
    public String page() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(
            @RequestParam(name = "nombres") String nombres,
            @RequestParam(name = "apellidos") String apellidos,
            @RequestParam(name = "direccion") String direccion,
            @RequestParam(name = "genero") String genero,
            @RequestParam(name = "ciudad") String ciudad,
            @RequestParam(name = "distrito") String distrito,
            @RequestParam(name = "fecha_nacimiento") String fechaNacimiento,
            @RequestParam(name = "dni") String dni,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            authService.register(nombres, apellidos, direccion, genero, ciudad, distrito, fechaNacimiento, dni, email, password);
            redirectAttributes.addFlashAttribute("success", "Registro exitoso. Ahora inicia sesión.");
            return "redirect:/login";
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/register";
        }
    }
}
