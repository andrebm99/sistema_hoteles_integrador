package com.springboot.sistema.hoteles.springboot_sistemahoteles.config;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.security.SessionUserSecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SessionUserSecurityFilter sessionUserSecurityFilter() {
    return new SessionUserSecurityFilter();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
        // públicos/estáticos
        .requestMatchers(
          "/", "/home", "/login", "/register",
          "/css/**", "/js/**", "/images/**", "/img/**", "/webjars/**",
          "/static/**", "/favicon.ico", "/error"
        ).permitAll()

        // área admin (solo ADMIN/RECEPCIONISTA/FINANZAS)
        .requestMatchers(
          "/api/admin/**",
          "/usuarios_admin", "/admin_redirect", "/rutas_admin/**",
          "/reservas_reportes", "/habitaciones", "/reportes", "/personal-hotel",
          "/reservas" // añade aquí las rutas admin que faltaban
        ).hasAnyRole("ADMIN","RECEPCIONISTA","FINANZAS")

        // resto lo maneja tu sesión; no forzar Security
        .anyRequest().permitAll()
      )
      // desactiva formLogin para que tu POST /login llegue al controlador
      .formLogin(form -> form.disable())
      // puente: convierte tu session.user en Authentication con roles
      .addFilterBefore(sessionUserSecurityFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}