package com.springboot.sistema.hoteles.springboot_sistemahoteles.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Habitacion;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.HabitacionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class HabitacionController {
    @Autowired
    HabitacionRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/habitaciones")
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

    @GetMapping("/habitaciones/{id_habitacion}")
    public ResponseEntity<Habitacion> getById(@PathVariable("id_habitacion") Long id_habitacion) {
        Optional<Habitacion> entidad = repository.findById(id_habitacion);
        if (entidad.isPresent()) {
            return new ResponseEntity<>(entidad.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/habitaciones", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Habitacion> create(
            @RequestPart("data") String habitacionJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            Habitacion entidad = objectMapper.readValue(habitacionJson, Habitacion.class);

            if (file != null && !file.isEmpty()) {
                entidad.setFoto_portada(file.getBytes());
                entidad.setFoto_portada_content_type(file.getContentType());
                entidad.setFoto_portada_filename(file.getOriginalFilename());
            }

            Habitacion _entidad = repository.save(entidad);
            return new ResponseEntity<>(_entidad, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/habitaciones/{id_habitacion}")
    public ResponseEntity<Habitacion> update(@PathVariable("id_habitacion") Long id_habitacion,
            @RequestBody Habitacion entidad) {
        Habitacion _entidad = repository.findById(id_habitacion).orElse(null);
        if (_entidad != null) {
            _entidad.setNumerohabitacion(entidad.getNumerohabitacion());
            _entidad.setNombre_comercial(entidad.getNombre_comercial());
            _entidad.setDescripcion(entidad.getDescripcion());
            _entidad.setMedidas(entidad.getMedidas());
            _entidad.setVista(entidad.getVista());
            _entidad.setEstado_operativo(entidad.getEstado_operativo());
            _entidad.setCapacidad_total(entidad.getCapacidad_total());
            _entidad.setPrecio(entidad.getPrecio());
            return new ResponseEntity<>(repository.save(_entidad), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/habitaciones/{id_habitacion}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Habitacion> updateFoto(
            @PathVariable("id_habitacion") Long id_habitacion,
            @RequestPart("file") MultipartFile file) {
                try{
                    Optional<Habitacion> optional = repository.findById(id_habitacion);

                    if(!optional.isPresent()){
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }

                    Habitacion habitacion = optional.get();

                    if(file != null && !file.isEmpty()){
                        habitacion.setFoto_portada(file.getBytes());
                        habitacion.setFoto_portada_content_type(file.getContentType());
                        habitacion.setFoto_portada_filename(file.getOriginalFilename());
                        Habitacion guardar = repository.save(habitacion);
                        return new ResponseEntity<>(guardar, HttpStatus.OK);
                    } else{
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                } catch(IOException e){
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
                } catch(Exception e){
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
                }
            }

    @DeleteMapping("/habitaciones/{id_habitacion}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id_habitacion") Long id_habitacion) {
        try {
            repository.deleteById(id_habitacion);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @GetMapping("/habitaciones/{id_habitacion}/foto")
    public ResponseEntity<byte[]> getFoto(@PathVariable("id_habitacion") Long id_habitacion) {
        Optional<Habitacion> optional = repository.findById(id_habitacion);

        if(!optional.isPresent()){
            return ResponseEntity.notFound().build();
        }

        Habitacion habitacion = optional.get();

        byte[] image = habitacion.getFoto_portada();
        if(image == null || image.length == 0){
            return ResponseEntity.noContent().build();
        } 

        String tipoContenido = habitacion.getFoto_portada_content_type();

        MediaType mediaType;

        try {
            mediaType = (tipoContenido != null) ? MediaType.parseMediaType(tipoContenido) : MediaType.APPLICATION_OCTET_STREAM;
        } catch (Exception e) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }

        return ResponseEntity.ok()
            .contentType(mediaType)
            .contentLength(image.length)
            .body(image);
    }   

}
