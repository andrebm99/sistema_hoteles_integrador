package com.springboot.sistema.hoteles.springboot_sistemahoteles.api;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.UsuarioCliente;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.services.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PageController {

  @Autowired
  private AuthService authService;

  @GetMapping("/home")
  public String home() { 
    return "home"; 
  }

  @GetMapping("/ajustescuenta")
  public String ajustesCuenta(HttpSession session, Model model) {
      Object userObject = session.getAttribute("user");
      if (userObject == null) {
          return "redirect:/login";
      }
      model.addAttribute("user", userObject);
      return "ajustescuenta";
  }

  @PostMapping("/ajustescuenta")
  public String updateAjustesCuenta(
          @RequestParam(name = "nombres", required = false) String nombres,
          @RequestParam(name = "apellidos", required = false) String apellidos,
          @RequestParam(name = "direccion", required = false) String direccion,
          @RequestParam(name = "genero", required = false) String genero,
          @RequestParam(name = "ciudad", required = false) String ciudad,
          @RequestParam(name = "distrito", required = false) String distrito,
          @RequestParam(name = "fechaNacimiento", required = false) String fechaNacimiento,
          @RequestParam(name = "dni", required = false) String dni,
          @RequestParam(name = "email", required = false) String email,
          HttpSession session,
          RedirectAttributes ra) {
      
      UsuarioCliente currentUser = (UsuarioCliente) session.getAttribute("user");
      if (currentUser == null) {
          ra.addFlashAttribute("error", "Tu sesión ha expirado. Por favor, inicia sesión de nuevo.");
          return "redirect:/login";
      }

      try {
          // Llama al servicio para actualizar el perfil
          UsuarioCliente updatedUser = authService.updateProfile(
              currentUser.getId(), nombres, apellidos, direccion, genero, ciudad, distrito, fechaNacimiento, dni, email);
          
          // Actualiza el objeto de usuario completo en la sesión
          session.setAttribute("user", updatedUser);
          
          ra.addFlashAttribute("success", "¡Tu perfil ha sido actualizado con éxito!");
      } catch (IllegalArgumentException ex) {
          ra.addFlashAttribute("error", ex.getMessage());
      }
      
      return "redirect:/ajustescuenta";
  }
}
