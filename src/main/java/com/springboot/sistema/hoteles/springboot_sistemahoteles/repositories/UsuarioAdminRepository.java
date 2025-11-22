package com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Usuario_Administracion;

public interface UsuarioAdminRepository extends JpaRepository<Usuario_Administracion, Long>{
    Optional<Usuario_Administracion> findByEmailAndPasswordHash(String email, String passwordHash);
}
