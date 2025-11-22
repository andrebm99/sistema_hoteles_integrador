package com.springboot.sistema.hoteles.springboot_sistemahoteles.services;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.UsuarioCliente;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.UsuarioAdminRepository;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.UsuarioClienteRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class AuthService {

    @Autowired
    private UsuarioClienteRepository usuarioClienteRepository;

    @Autowired
    private UsuarioAdminRepository usuarioAdminRepository;

    @Transactional(readOnly = true)
    public Optional<? extends Object> authenticate(String email, String password, String userType) {
        if ("admin".equals(userType)) {
            return usuarioAdminRepository.findByEmailAndPasswordHash(email, password);
        } else {
            return usuarioClienteRepository.findByEmailAndPasswordHash(email, password);
        }
    }

    @Transactional
    public UsuarioCliente register(String nombres, String apellidos, String direccion, String genero, String ciudad, String distrito, String fechaNacimiento, String dni, String email, String password) {
        UsuarioCliente newUser = new UsuarioCliente();

        newUser.setNombres(nombres);
        newUser.setApellidos(apellidos);
        newUser.setDireccion(direccion);
        newUser.setGenero(genero);
        newUser.setCiudad(ciudad);
        newUser.setDistrito(distrito);
        newUser.setDni(dni);
        newUser.setEmail(email);

        // Guarda la contraseña 
        newUser.setPasswordHash(password);
        
        if (StringUtils.hasText(fechaNacimiento)) {
            try {
                LocalDate date = LocalDate.parse(fechaNacimiento, DateTimeFormatter.ISO_LOCAL_DATE);
                newUser.setFechaNacimiento(date);
            } catch (Exception e) {
                 System.err.println("Error al parsear la fecha: " + e.getMessage());
            }
        }

        newUser.setFechaCreado(LocalDateTime.now());
        newUser.setFechaActualizacion(LocalDateTime.now());

        return usuarioClienteRepository.save(newUser);
    }

    @Transactional
    @NonNull
    public UsuarioCliente updateProfile(
                Long userId,
                String nombres,
                String apellidos,
                String direccion,
                String genero,
                String ciudad,
                String distrito,
                String fechaNacimiento,
                String dni,
                String email) {
    
            if (userId == null) {
                throw new IllegalArgumentException("El ID de usuario no puede ser nulo");
            }
    
            UsuarioCliente user = usuarioClienteRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    
            // Actualiza los campos solo si se proporcionan nuevos valores
            if (StringUtils.hasText(nombres)) {
                user.setNombres(nombres);
            }
            if (StringUtils.hasText(apellidos)) {
                user.setApellidos(apellidos);
            }
            if (StringUtils.hasText(direccion)) {
                user.setDireccion(direccion);
            }
            if (StringUtils.hasText(genero)) {
                user.setGenero(genero);
            }
            if (StringUtils.hasText(ciudad)) {
                user.setCiudad(ciudad);
            }
            if (StringUtils.hasText(distrito)) {
                user.setDistrito(distrito);
            }
            if (StringUtils.hasText(dni)) {
                user.setDni(dni);
            }
            if (StringUtils.hasText(email)) {
                user.setEmail(email);
            }
            if (StringUtils.hasText(fechaNacimiento)) {
                try {
                    LocalDate date = LocalDate.parse(fechaNacimiento, DateTimeFormatter.ISO_LOCAL_DATE);
                    user.setFechaNacimiento(date);
                } catch (Exception e) {
                    System.err.println("Error al parsear la fecha: " + e.getMessage());
                }
            }
            user.setFechaActualizacion(LocalDateTime.now());
    
            return Objects.requireNonNull(usuarioClienteRepository.save(user));
    }
    
}
