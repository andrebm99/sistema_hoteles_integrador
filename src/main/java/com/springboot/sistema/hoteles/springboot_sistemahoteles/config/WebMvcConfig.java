package com.springboot.sistema.hoteles.springboot_sistemahoteles.config;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.interceptors.AdminAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new AdminAuthInterceptor())
      .addPathPatterns(
        "/reservas",
        "/api/admin/**",
        "/usuarios_admin", "/admin_redirect", "/rutas_admin/**",
        "/reservas_reportes", "/habitaciones", "/reportes", "/personal-hotel"
      );
  }
}