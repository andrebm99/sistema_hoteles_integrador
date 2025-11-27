package com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Usuario_Administracion;

public interface UsuarioAdministracionRepository extends JpaRepository<Usuario_Administracion, Long> {
    @Query("select distinct u from Usuario_Administracion u left join fetch u.roles")
    List<Usuario_Administracion> findAllWithRoles();

    boolean existsByEmailIgnoreCase(String email); 
}