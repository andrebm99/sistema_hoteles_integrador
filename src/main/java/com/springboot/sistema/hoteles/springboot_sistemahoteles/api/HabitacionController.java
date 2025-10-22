package com.springboot.sistema.hoteles.springboot_sistemahoteles.api;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Habitacion;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.HabitacionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class HabitacionController {
    @Autowired
    HabitacionRepository repository;

    @GetMapping("/habitacion")
    public ResponseEntity<List<Habitacion>> getAll(@RequestParam(required = false) String title) {
        try {
            List<Habitacion> lista = new ArrayList<Habitacion>();
            repository.findAll().forEach(lista::add);
            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/habitacion/{id_habitacion}")
    public ResponseEntity<Habitacion> getById(@PathVariable("id_habitacion") Long id_habitacion) {
        Optional<Habitacion> entidad = repository.findById(id_habitacion);
        if (entidad.isPresent()) {
            return new ResponseEntity<>(entidad.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/habitacion")
    public ResponseEntity<Habitacion> create(@RequestBody Habitacion entidad){
        try{
            Habitacion _entidad = repository.save(new Habitacion(null, entidad.getNumero_habitacion(), entidad.getNombre_comercial(), entidad.getDescripcion(), entidad.getMedidas(), entidad.getVista(), entidad.getEstado_operativo(), entidad.getFoto_portada_url(), entidad.getCapacidad_total() ));
            return new ResponseEntity<>(_entidad, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    @PutMapping("/habitacion/{id_habitacion}")
    public ResponseEntity<Habitacion> update(@PathVariable("id_habitacion") Long id_habitacion, @RequestBody Habitacion entidad) {
        Habitacion _entidad = repository.findById(id_habitacion).orElse(null);
        if (_entidad != null) {
            _entidad.setNumero_habitacion(entidad.getNumero_habitacion());
            _entidad.setNombre_comercial(entidad.getNombre_comercial());
            _entidad.setDescripcion(entidad.getDescripcion());
            _entidad.setMedidas(entidad.getMedidas());
            _entidad.setVista(entidad.getVista());
            _entidad.setEstado_operativo(entidad.getEstado_operativo());
            _entidad.setFoto_portada_url(entidad.getFoto_portada_url());
            _entidad.setCapacidad_total(entidad.getCapacidad_total());
            return new ResponseEntity<>(repository.save(_entidad), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    

    @DeleteMapping("/habitacion/{id_habitacion}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id_habitacion") Long id_habitacion) {
        try {
            repository.deleteById(id_habitacion);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
