package com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.UsuarioCliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioClienteRepository extends JpaRepository<UsuarioCliente, Long> {

    Optional<UsuarioCliente> findByEmailAndPasswordHash(String email, String passwordHash);

}
