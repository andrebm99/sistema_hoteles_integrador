package com.springboot.sistema.hoteles.springboot_sistemahoteles.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.UsuarioCliente;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.services.AuthService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AjusteCuentaController {

    @Autowired
    private AuthService authService;

    @GetMapping("/ajustecuenta")
    public String showAjusteCuentaPage(HttpSession session, Model model) {
        UsuarioCliente user = (UsuarioCliente) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; 
        }
        model.addAttribute("user", user);
        return "ajustecuenta"; 
    }

    @PostMapping("/ajustecuenta")
    public String handleAjusteCuenta(
            @RequestParam(name = "nombres") String nombres,
            @RequestParam(name = "apellidos") String apellidos,
            @RequestParam(name = "direccion") String direccion,
            @RequestParam(name = "genero") String genero,
            @RequestParam(name = "ciudad") String ciudad,
            @RequestParam(name = "distrito") String distrito,
            @RequestParam(name = "fecha_nacimiento") String fechaNacimiento,
            @RequestParam(name = "dni") String dni,
            @RequestParam(name = "email") String email,
            HttpSession session, 
            RedirectAttributes redirectAttributes) {

        UsuarioCliente currentUser = (UsuarioCliente) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            UsuarioCliente updatedUser = authService.updateProfile(
                currentUser.getId(), 
                nombres, 
                apellidos, 
                direccion, 
                genero, 
                ciudad, 
                distrito, 
                fechaNacimiento, 
                dni, 
                email
            );

            // Actualiza el objeto de usuario en la sesión
            session.setAttribute("user", updatedUser);
            redirectAttributes.addFlashAttribute("success", "¡Tu información ha sido actualizada exitosamente!");

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar: " + e.getMessage());
        }

        return "redirect:/ajustecuenta";
    }
}
