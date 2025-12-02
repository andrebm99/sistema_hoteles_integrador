package com.springboot.sistema.hoteles.springboot_sistemahoteles.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Usuario_Administracion;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component("adminAuthInterceptor")
public class AdminAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");

        if (user instanceof Usuario_Administracion) {
            return true; 
        }

        if (user != null) {
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
        
        return false; 
    }

}
