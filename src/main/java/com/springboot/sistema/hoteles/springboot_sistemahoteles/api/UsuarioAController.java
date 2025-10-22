package com.springboot.sistema.hoteles.springboot_sistemahoteles.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Usuario_Administracion;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.UsuarioAdminRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UsuarioAController {
    @Autowired
    UsuarioAdminRepository repository;

    @GetMapping("/usuario_admin")
    public ResponseEntity<List<Usuario_Administracion>> getAll(@RequestParam(required = false) String title) {
        try {
            List<Usuario_Administracion> lista = new ArrayList<Usuario_Administracion>();
            repository.findAll().forEach(lista::add);
            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/usuario_admin/{id}")
    public ResponseEntity<Usuario_Administracion> getById(@PathVariable("id") Long id) {
        Optional<Usuario_Administracion> entidad = repository.findById(id);
        if (entidad.isPresent()) {
            return new ResponseEntity<>(entidad.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/usuario_admin")
    public ResponseEntity<Usuario_Administracion> create(@RequestBody Usuario_Administracion entidad){
        try{
            Usuario_Administracion _entidad = repository.save(new Usuario_Administracion(
                null, 
                entidad.getNombres_apellidos(),
                entidad.getEmail(),
                entidad.getRol_id(),
                entidad.getEstado(),
                entidad.getFecha_creado(),
                entidad.getFecha_actualizacion()
            ));
            return new ResponseEntity<>(_entidad, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    @PutMapping("/usuario_admin/{id}")
    public ResponseEntity<Usuario_Administracion> update(@PathVariable("id") Long id, @RequestBody Usuario_Administracion entidad) {
        Usuario_Administracion _entidad = repository.findById(id).orElse(null);
        if (_entidad != null) {
            _entidad.setNombres_apellidos(entidad.getNombres_apellidos());
            _entidad.setEmail(entidad.getEmail());
            _entidad.setRol_id(entidad.getRol_id());
            _entidad.setEstado(entidad.getEstado());
            _entidad.setFecha_creado(entidad.getFecha_creado());
            _entidad.setFecha_actualizacion(entidad.getFecha_actualizacion());
            return new ResponseEntity<>(repository.save(_entidad), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/usuario_admin/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
