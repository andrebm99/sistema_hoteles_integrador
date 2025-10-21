package com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long>{
    
}
