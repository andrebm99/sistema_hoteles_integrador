package com.springboot.sistema.hoteles.springboot_sistemahoteles.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Usuario_Administracion;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.UsuarioAdminRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UsuarioAController {
    
    @Autowired
    UsuarioAdminRepository repository;

    @GetMapping("/usuario_admin")
    public ResponseEntity<List<Usuario_Administracion>> getAll(@RequestParam(required = false) String requerido){
        try{
            List<Usuario_Administracion> items = new ArrayList<Usuario_Administracion>();
            if(requerido == null){
                repository.findAll().forEach(items::add);
            } else {
                repository.findAll().forEach(items::add);
            }
            if(items.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items, HttpStatus.OK);

        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/usuario_admin")
    public ResponseEntity<Usuario_Administracion> create(@RequestBody Usuario_Administracion entidad){
        try{
            Usuario_Administracion newAdmin = new Usuario_Administracion();
            newAdmin.setNombres_apellidos(entidad.getNombres_apellidos());
            newAdmin.setEmail(entidad.getEmail());
            newAdmin.setPasswordHash(entidad.getPasswordHash());
            newAdmin.setRol_id(entidad.getRol_id());
            newAdmin.setEstado(entidad.getEstado());
            newAdmin.setFecha_creado(entidad.getFecha_creado());
            newAdmin.setFecha_actualizacion(entidad.getFecha_actualizacion());
            
            Usuario_Administracion _entidad = repository.save(newAdmin);
            return new ResponseEntity<>(_entidad, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    @PutMapping("/usuario_admin/{id}")
    public ResponseEntity<Usuario_Administracion> update(@PathVariable("id") long id, @RequestBody Usuario_Administracion entidad) {
        Optional<Usuario_Administracion> subjectData = repository.findById(id);

        if (subjectData.isPresent()) {
            Usuario_Administracion _subject = subjectData.get();
            _subject.setNombres_apellidos(entidad.getNombres_apellidos());
            _subject.setEmail(entidad.getEmail());
            _subject.setRol_id(entidad.getRol_id());
            _subject.setEstado(entidad.getEstado());
            _subject.setFecha_creado(entidad.getFecha_creado());
            _subject.setFecha_actualizacion(entidad.getFecha_actualizacion());
        return new ResponseEntity<>(repository.save(_subject), HttpStatus.OK);
        } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/usuario_admin/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
        try {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/usuario_admin")
    public ResponseEntity<HttpStatus> deleteAll() {
        try {
        repository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
