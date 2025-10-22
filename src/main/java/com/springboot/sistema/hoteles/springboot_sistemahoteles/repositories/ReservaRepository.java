package com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long>{
    
}
