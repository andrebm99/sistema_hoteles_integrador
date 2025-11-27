package com.springboot.sistema.hoteles.springboot_sistemahoteles.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Habitacion;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.HabitacionRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class HabitacionService {
    private final HabitacionRepository repositorio;

    public HabitacionService(HabitacionRepository repositorio) {
        this.repositorio = repositorio;
    }

    @SuppressWarnings("null")
    @Transactional
    public Habitacion create(Habitacion habitacion, MultipartFile file) throws IOException{
        if(file != null && !file.isEmpty()){
            habitacion.setFoto_portada(file.getBytes());
            habitacion.setFoto_portada_content_type(file.getContentType());
            habitacion.setFoto_portada_filename(file.getOriginalFilename());
        }
        return repositorio.save(habitacion); 
    }

    @Transactional(readOnly = true)
    public Habitacion findById(long id){
        return repositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada.")); 
    }
}
