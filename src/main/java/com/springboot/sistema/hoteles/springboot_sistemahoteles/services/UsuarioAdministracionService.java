package com.springboot.sistema.hoteles.springboot_sistemahoteles.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Roles;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Usuario_Administracion;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.RolesRespository;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.UsuarioAdministracionRepository;

@Service
public class UsuarioAdministracionService {

    private final UsuarioAdministracionRepository userRepo;
    private final RolesRespository rolesRepo;
    private final PasswordEncoder passwordEncoder; 

    public UsuarioAdministracionService(UsuarioAdministracionRepository userRepo, RolesRespository rolesRepo,
            PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.rolesRepo = rolesRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario_Administracion crearUsuario(String nombres, String apellidos, String email, String password) {
        if (!StringUtils.hasText(nombres) || !StringUtils.hasText(apellidos) || !StringUtils.hasText(email) || !StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }
        if (userRepo.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        Usuario_Administracion u = new Usuario_Administracion();
        u.setNombres_apellidos(nombres + " " + apellidos);
        u.setEmail(email);
        u.setPasswordHash(passwordEncoder.encode(password)); 
        u.setRol_id(null);
        return userRepo.save(u);
    }

    @SuppressWarnings("null")
    public Usuario_Administracion actualizarUsuario(long id, String nombresApellidos, String email, String password) {
        Usuario_Administracion u = userRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        if (StringUtils.hasText(nombresApellidos)) u.setNombres_apellidos(nombresApellidos);
        if (StringUtils.hasText(email)) u.setEmail(email);
        if (StringUtils.hasText(password)) u.setPasswordHash(passwordEncoder.encode(password));
        return userRepo.save(u);
    }

    public void eliminarUsuario(long id) {
        userRepo.deleteById(id);
    }

    public List<Usuario_Administracion> listarUsuarios() {
        // No uses findAllWithRoles si trabajas con un solo rol_id
        return userRepo.findAll();
    }

    public Usuario_Administracion asignarRol(long userId, long rolId) {
        Usuario_Administracion u = userRepo.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Roles r = rolesRepo.findById(rolId)
            .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
        u.setRol_id(r.getId_rol());
        return userRepo.save(u);
    }

    public Usuario_Administracion quitarRol(long userId, long rolId) {
        Usuario_Administracion u = userRepo.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        // Quitar solo si coincide el rol actual
        if (u.getRol_id() != null && u.getRol_id().equals(rolId)) {
            u.setRol_id(null);
            return userRepo.save(u);
        }
        return u;
    }
}