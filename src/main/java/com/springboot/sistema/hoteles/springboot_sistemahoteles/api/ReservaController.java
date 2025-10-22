package com.springboot.sistema.hoteles.springboot_sistemahoteles.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Reserva;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.ReservaRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ReservaController {
    @Autowired
    ReservaRepository repository;

    @GetMapping("/reserva")
    public ResponseEntity<List<Reserva>> getAll(@RequestParam(required = false) String title) {
        try {
            List<Reserva> lista = new ArrayList<Reserva>();
            repository.findAll().forEach(lista::add);
            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reserva/{id_reserva}")
    public ResponseEntity<Reserva> getById(@PathVariable("id_reserva") Long id_reserva) {
        Optional<Reserva> entidad = repository.findById(id_reserva);
        if (entidad.isPresent()) {
            return new ResponseEntity<>(entidad.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/reserva")
    public ResponseEntity<Reserva> create(@RequestBody Reserva entidad){
        try{
            Reserva _entidad = repository.save(new Reserva(
                null, 
                entidad.getNombres_apellidos(),
                entidad.getDni(),
                entidad.getHabitacion(),
                entidad.getPiso(),
                entidad.getFechaInicio(),
                entidad.getFecha_salida(),
                entidad.getMetodo_pago()
            ));
            return new ResponseEntity<>(_entidad, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    @PutMapping("/reserva/{id_reserva}")
    public ResponseEntity<Reserva> update(@PathVariable("id_reserva") Long id_reserva, @RequestBody Reserva entidad) {
        Reserva _entidad = repository.findById(id_reserva).orElse(null);
        if (_entidad != null) {
            _entidad.setNombres_apellidos(entidad.getNombres_apellidos());
            _entidad.setDni(entidad.getDni());
            _entidad.setHabitacion(entidad.getHabitacion());
            _entidad.setPiso(entidad.getPiso());
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
