package com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Alumno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository extends JpaRepository<Alumno, Long>{
    List<Alumno> findByNombresContaining(String nombres);
    List<Alumno> findByNombres(String nombres); 
}
