package com.springboot.sistema.hoteles.springboot_sistemahoteles.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Reserva;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Habitacion;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.HabitacionRepository;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.ReservaRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ReservaController {

    private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);

    @Autowired
    ReservaRepository repository;
    @Autowired
    HabitacionRepository habitacionRepository;

    @GetMapping("/reservas/habitaciones/{id_habitacion}/foto")
    public ResponseEntity<byte[]> getFoto(@PathVariable("id_habitacion") Long id_habitacion) {
        Optional<Habitacion> habitacionOpt = habitacionRepository.findById(id_habitacion);

        if (!habitacionOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Habitacion habitacion = habitacionOpt.get();

        byte[] image = habitacion.getFoto_portada();
        if (image == null || image.length == 0) {
            return ResponseEntity.noContent().build();
        }

        String tipoContenido = habitacion.getFoto_portada_content_type();
        MediaType mediaType;

        try {
            mediaType = (tipoContenido != null) ? MediaType.parseMediaType(tipoContenido)
                    : MediaType.APPLICATION_OCTET_STREAM;
        } catch (Exception e) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .contentLength(image.length)
                .body(image);
    }

    @SuppressWarnings("null")
    @GetMapping("/reserva")
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "id_reserva") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String codigo,
            @RequestParam(required = false) String nombresapellidos,
            @RequestParam(required = false) Integer dni,
            @RequestParam(required = false) String habitacion) {
        try {
            if (size < 1)
                size = 10;
            if (size > 100)
                size = 100;
            if (page < 0)
                page = 0;

            Set<String> allowed = Set.of("id_reserva", "codigo", "nombresapellidos", "dni", "habitacion", "fechaInicio",
                    "fecha_salida");

            String sortProperty = sortBy;
            if ("id".equalsIgnoreCase(sortBy) || "idReserva".equalsIgnoreCase(sortBy)) {
                sortProperty = "id_reserva";
            }

            if (!allowed.contains(sortProperty)) {
                logger.warn("Sort property '{}' no permitida, usando 'id_reserva' por defecto", sortBy);
                sortProperty = "id_reserva";
            }

            Sort sort;
            try {
                sort = Sort.by(Sort.Direction.fromString(sortDir), sortProperty);
            } catch (IllegalArgumentException e) {
                sort = Sort.by("id_reserva").ascending();
            }

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Reserva> pageResult;

            try {
                pageResult = repository.buscarReservas(codigo, nombresapellidos, dni, habitacion, pageable);
            } catch (org.springframework.data.mapping.PropertyReferenceException pre) {
                logger.warn("Propiedad de ordenamiento inválida ('{}'), intentando sin ordenamiento", sortBy);
                pageable = PageRequest.of(page, size);
                pageResult = repository.buscarReservas(codigo, nombresapellidos, dni, habitacion, pageable);
            }

            Map<String, Object> resp = new HashMap<>();
            resp.put("content", pageResult.getContent());
            resp.put("totalElements", pageResult.getTotalElements());
            resp.put("totalPages", pageResult.getTotalPages());
            resp.put("page", pageResult.getNumber());
            resp.put("size", pageResult.getSize());

            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error listado reservas", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reserva/codigo/{codigo}")
    public ResponseEntity<Reserva> getByCodigo(@PathVariable("codigo") String codigo) {
        try {
            Optional<Reserva> entidad = repository.findByCodigo(codigo);
            if (entidad.isPresent()) {
                return new ResponseEntity<>(entidad.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reserva/nombresapellidos/{nombresapellidos}")
    public ResponseEntity<Reserva> getByName(@PathVariable String nombresapellidos) {
        try {
            Optional<Reserva> entidad = repository.findByNombresapellidos(nombresapellidos);
            if (entidad.isPresent()) {
                return new ResponseEntity<>(entidad.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reserva/dni/{dni}")
    public ResponseEntity<Reserva> getByDni(@PathVariable Integer dni) {
        try {
            Optional<Reserva> entidad = repository.findByDni(dni);
            if (entidad.isPresent()) {
                return new ResponseEntity<>(entidad.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reserva/habitacion/{habitacion}")
    public ResponseEntity<List<Reserva>> getByHabitacion(@PathVariable String habitacion) {
        try {
            List<Reserva> entidad = repository.findByHabitacionContainingIgnoreCase(habitacion);
            return ResponseEntity.ok(entidad); // 200 incluso si está vacía
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/reserva")
    public ResponseEntity<?> create(@RequestBody Reserva entidad) {
        try {
            if (entidad == null || entidad.getFechaInicio() == null || entidad.getFecha_salida() == null
                    || entidad.getHabitacion() == null) {
                return ResponseEntity.badRequest().body("Faltan datos (fecha inicio/fin o tipo de habitación).");
            }

            LocalDateTime inicio = entidad.getFechaInicio();
            LocalDateTime fin = entidad.getFecha_salida();
            String tipo = entidad.getHabitacion();

            List<Habitacion> habitaciones = habitacionRepository.findListByNombreComercialIgnoreCase(tipo);
            if (habitaciones == null || habitaciones.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existen habitaciones del tipo solicitado.");
            }

            for (Habitacion h : habitaciones) {
                Long roomId = h.getId_habitacion();
                List<Reserva> conflictos = repository.findConflictingReservationsByRoomId(roomId, inicio, fin);
                if (conflictos == null || conflictos.isEmpty()) {
                    entidad.setId_habitacion(roomId);

                    // Asignar habitación y número
                    String numero = (h.getNumerohabitacion() != null) ? (" #" + h.getNumerohabitacion()) : "";
                    entidad.setHabitacion(h.getNombre_comercial() + numero);

                    // Calcular
                    long horas = java.time.Duration.between(inicio, fin).toHours();
                    long noches = Math.max(1, horas / 24);

                    Double precioUnitario = h.getPrecio() != null ? h.getPrecio() : 0.0;
                    Double totalAplicado = precioUnitario * noches;

                    entidad.setPrecio_aplicado(precioUnitario);
                    entidad.setTotal_aplicado(totalAplicado);

                    entidad.setId_reserva(null);

                    Reserva saved = repository.save(entidad);

                    h.setEstado_operativo("Ocupada");
                    habitacionRepository.save(h);

                    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
                }
            }

            // Si llegamos aquí, no hay habitaciones libres
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No hay habitaciones libres para ese rango de fechas.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("/reserva/{id_reserva}")
    public ResponseEntity<?> update(@PathVariable long id_reserva, @RequestBody Reserva entidad) {

        Reserva _entidad = repository.findById(id_reserva).orElse(null);

        if (_entidad == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (entidad.getHabitacion() == null || entidad.getFechaInicio() == null || entidad.getFecha_salida() == null) {
            return ResponseEntity.badRequest().body("Habitación, fecha de inicio y fecha de salida son obligatórios.");
        }

        boolean existeHabitacion = habitacionRepository.existsByNombreComercialIgnoreCase(entidad.getHabitacion());

        if (!existeHabitacion) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La habitación indicada no existe.");
        }

        List<Reserva> conflicts = repository.findConflictingReservations(
                entidad.getHabitacion(), entidad.getFechaInicio(), entidad.getFecha_salida());

        conflicts.removeIf(r -> r.getId_reserva().equals(id_reserva));

        if (!conflicts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("La habitación no está disponible en las fechas indicadas.");
        }

        _entidad.setCodigo(entidad.getCodigo());
        _entidad.setNombresapellidos(entidad.getNombresapellidos());
        _entidad.setDni(entidad.getDni());
        _entidad.setEdad(entidad.getEdad());
        _entidad.setHabitacion(entidad.getHabitacion());
        _entidad.setOcupantes(entidad.getOcupantes());
        _entidad.setFechaInicio(entidad.getFechaInicio());
        _entidad.setFecha_salida(entidad.getFecha_salida());
        _entidad.setMetodo_pago(entidad.getMetodo_pago());
        return new ResponseEntity<>(repository.save(_entidad), HttpStatus.OK);

    }

    @SuppressWarnings("null")
    @DeleteMapping("/reserva/{id_reserva}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id_reserva) {
        try {

            Reserva reserva = repository.findById(id_reserva).orElse(null);

            if (reserva == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            repository.deleteById(id_reserva);

            if (reserva.getId_habitacion() != null) {
                habitacionRepository.findById(reserva.getId_habitacion()).ifPresent(h -> {
                    h.setEstado_operativo("Disponible");
                    habitacionRepository.save(h);
                });
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reserva/disponibilidad")
    public ResponseEntity<Map<String, Object>> disponibilidad(
            @RequestParam String habitacion,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        try {
            LocalDateTime inicio = LocalDateTime.parse(fechaInicio);
            LocalDateTime fin = LocalDateTime.parse(fechaFin);
            List<Reserva> conflicts = repository.findConflictingReservations(habitacion, inicio, fin);
            Map<String, Object> resp = new HashMap<>();
            resp.put("habitacion", habitacion);
            resp.put("disponible", conflicts.isEmpty());
            resp.put("conflictos", conflicts);
            return ResponseEntity.ok(resp);
        } catch (DateTimeParseException exception) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Formato invalido de fecha, debe usar formato iso 'Ejemplo: yyyy-MM-ddTHH:mm:ss'"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/reserva/{id_reserva}")
    public ResponseEntity<Reserva> getById(@PathVariable("id_reserva") long id_reserva) {
        Optional<Reserva> optional = repository.findById(id_reserva);
        return optional.map(res -> new ResponseEntity<>(res, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
