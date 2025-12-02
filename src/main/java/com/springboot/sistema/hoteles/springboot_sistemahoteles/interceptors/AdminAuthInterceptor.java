package com.springboot.sistema.hoteles.springboot_sistemahoteles.interceptors;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.UsuarioCliente;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Usuario_Administracion;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminAuthInterceptor implements HandlerInterceptor {

  private boolean isAdminRole(Long rol) {
    return rol != null && (rol == 1L || rol == 2L || rol == 3L);
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HttpSession session = request.getSession(false);
    Object user = (session == null) ? null : session.getAttribute("user");

    if (user == null) {
      response.sendRedirect("/login");
      return false;
    }

    if (user instanceof Usuario_Administracion admin) {
      if (isAdminRole(admin.getRol_id())) {
        return true;
      }
      response.sendRedirect("/home");
      return false;
    }

    if (user instanceof UsuarioCliente) {
      response.sendRedirect("/home");
      return false;
    }

    response.sendRedirect("/login");
    return false;
  }
}