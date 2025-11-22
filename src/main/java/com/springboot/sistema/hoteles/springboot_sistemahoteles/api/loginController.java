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
public class loginController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String page(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        return authService.authenticate(email, password)
                .map(user -> {
                    session.setAttribute("user", user);
                    redirectAttributes.addFlashAttribute("success", "¡Bienvenido/a de nuevo, " + user.getNombres() + "!");
                    return "redirect:/home";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Correo o contraseña incorrectos");
                    return "redirect:/login";
                });
    }

    @GetMapping("/logout")
    public String doLogout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "Has cerrado sesión correctamente.");
        return "redirect:/home";
    }
}
