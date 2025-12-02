package com.springboot.sistema.hoteles.springboot_sistemahoteles.security;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.UsuarioCliente;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Usuario_Administracion;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class SessionUserSecurityFilter extends OncePerRequestFilter {

  private boolean isAdminRole(Long rol) {
    return rol != null && (rol == 1L || rol == 2L || rol == 3L);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      HttpSession session = request.getSession(false);
      if (session != null) {
        Object user = session.getAttribute("user");
        if (user instanceof Usuario_Administracion admin && isAdminRole(admin.getRol_id())) {
          String role = switch (admin.getRol_id().intValue()) {
            case 1 -> "ROLE_ADMIN";
            case 2 -> "ROLE_RECEPCIONISTA";
            case 3 -> "ROLE_FINANZAS";
            default -> "ROLE_CLIENTE";
          };
          var auth = new UsernamePasswordAuthenticationToken(admin, null, List.of(new SimpleGrantedAuthority(role)));
          SecurityContextHolder.getContext().setAuthentication(auth);
        } else if (user instanceof UsuarioCliente client) {
          var auth = new UsernamePasswordAuthenticationToken(client, null, List.of(new SimpleGrantedAuthority("ROLE_CLIENTE")));
          SecurityContextHolder.getContext().setAuthentication(auth);
        }
      }
    }
    chain.doFilter(request, response);
  }
}