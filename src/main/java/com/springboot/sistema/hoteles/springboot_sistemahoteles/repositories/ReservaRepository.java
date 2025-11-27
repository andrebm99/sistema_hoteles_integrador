package com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Reserva;


public interface ReservaRepository extends JpaRepository<Reserva, Long>{
    @Query("SELECT r FROM Reserva r WHERE " +
        "(:codigo IS NULL OR LOWER(r.codigo) = LOWER(:codigo)) AND " + 
        "(:nombresapellidos IS NULL OR LOWER(r.nombresapellidos) LIKE LOWER(CONCAT('%', :nombresapellidos, '%' ))) AND " +
        "(:dni IS NULL OR r.dni = :dni) AND " +
        "(:habitacion IS NULL OR LOWER(r.habitacion) = LOWER(:habitacion))"
    )
    Page<Reserva> buscarReservas(
        @Param("codigo") String codigo,
        @Param("nombresapellidos") String nombresapellidos,
        @Param("dni") Integer dni,
        @Param("habitacion") String habitacion,
        Pageable pageable
    );

    Optional<Reserva> findByCodigo(String codigo);
    Optional<Reserva> findByNombresapellidos(String nombresapellidos); 
    Optional<Reserva> findByDni(Integer dni); 
    List<Reserva> findByHabitacion(String habitacion);


        @Query("SELECT r FROM Reserva r WHERE r.id_habitacion = :roomId AND r.fechaInicio < :fechaFin AND r.fecha_salida > :fechaInicio")
        List<Reserva> findConflictingReservationsByRoomId(
            @Param("roomId") Long roomId,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
        );
    
    @Query("SELECT r FROM Reserva r WHERE LOWER(r.habitacion) = LOWER(:habitacion)"
    + "AND r.fechaInicio < :fechaFin AND r.fecha_salida > :fechaInicio") List<Reserva> findConflictingReservations(
         @Param("habitacion") String habitacion,
         @Param("fechaInicio") LocalDateTime fechaInicio,
         @Param("fechaFin") LocalDateTime fechaFin
    );
}
