package com.springboot.sistema.hoteles.springboot_sistemahoteles.api;

import java.util.ArrayList;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Reserva;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.ReservaRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ReservaController {

    private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);
    @Autowired
    ReservaRepository repository;

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
    public ResponseEntity<Reserva> getByName(@PathVariable("nombresapellidos") String nombresapellidos) {
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
    public ResponseEntity<Reserva> getByDni(@PathVariable("dni") Integer dni) {
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
    public ResponseEntity<List<Reserva>> getByHabitacion(@PathVariable("habitacion") String habitacion) {
        try {
            List<Reserva> entidad = repository.findByHabitacion(habitacion);
            if (entidad.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(entidad, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/reserva")
    public ResponseEntity<Reserva> create(@RequestBody Reserva entidad) {
        try {
            Reserva _entidad = repository.save(new Reserva(
                    null,
                    entidad.getCodigo(),
                    entidad.getNombresapellidos(),
                    entidad.getDni(),
                    entidad.getEdad(),
                    entidad.getHabitacion(),
                    entidad.getOcupantes(),
                    entidad.getFechaInicio(),
                    entidad.getFecha_salida(),
                    entidad.getMetodo_pago()));
            return new ResponseEntity<>(_entidad, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/reserva/{id_reserva}")
    public ResponseEntity<Reserva> update(@PathVariable("id_reserva") Long id_reserva, @RequestBody Reserva entidad) {
        Reserva _entidad = repository.findById(id_reserva).orElse(null);
        if (_entidad != null) {
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
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/reserva/{id_reserva}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id_reserva") Long id_reserva) {
        try {
            repository.deleteById(id_reserva);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
