package com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Habitacion;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long>{

    @Query("SELECT h FROM Habitacion h WHERE LOWER(h.nombre_comercial) = LOWER(:nombre)")
    Optional<Habitacion> findByNombreComercialIgnoreCase(@Param("nombre") String nombre);

    @Query("SELECT h FROM Habitacion h WHERE LOWER(h.nombre_comercial) = LOWER(:nombre)")
    List<Habitacion> findListByNombreComercialIgnoreCase(@Param("nombre") String nombre);

    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM Habitacion h WHERE LOWER(h.nombre_comercial) = LOWER(:nombre)")
    boolean existsByNombreComercialIgnoreCase(@Param("nombre") String nombre);

    boolean existsByNumerohabitacion(Integer numeroHabitacion);

}
