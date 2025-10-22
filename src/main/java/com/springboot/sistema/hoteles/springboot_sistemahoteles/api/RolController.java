package com.springboot.sistema.hoteles.springboot_sistemahoteles.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Roles;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.RolesRespository;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class RolController {
    @Autowired
    RolesRespository repository;

    @GetMapping("/rol")
    public ResponseEntity<List<Roles>> getAll(@RequestParam(required = false) String title) {
        try {
            List<Roles> lista = new ArrayList<Roles>();
            repository.findAll().forEach(lista::add);
            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/rol/{id_roles}")
    public ResponseEntity<Roles> getById(@PathVariable("id_roles") Long id_roles) {
        Optional<Roles> entidad = repository.findById(id_roles);
        if (entidad.isPresent()) {
            return new ResponseEntity<>(entidad.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/rol")
    public ResponseEntity<Roles> create(@RequestBody Roles entidad){
        try{
            Roles _entidad = repository.save(new Roles(
                null, 
                entidad.getNombre_rol(),
                entidad.getDescripcion()
            ));
            return new ResponseEntity<>(_entidad, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    @PutMapping("/rol/{id_rol}")
    public ResponseEntity<Roles> update(@PathVariable("id_rol") Long id_rol, @RequestBody Roles entidad) {
        Roles _entidad = repository.findById(id_rol).orElse(null);
        if (_entidad != null) {
            _entidad.setNombre_rol(entidad.getNombre_rol());
            _entidad.setDescripcion(entidad.getDescripcion());
            return new ResponseEntity<>(repository.save(_entidad), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    

    @DeleteMapping("/rol/{id_rol}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id_rol") Long id_rol) {
        try {
            repository.deleteById(id_rol);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
