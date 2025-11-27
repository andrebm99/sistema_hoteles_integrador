package com.springboot.sistema.hoteles.springboot_sistemahoteles.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.ReporteMensual;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.ReporteMensualRepository;

@Service
public class ReporteMensualService {
    private final ReporteMensualRepository repository;

    public ReporteMensualService(ReporteMensualRepository repository){
        this.repository = repository; 
    }

    public ReporteMensual subir(Integer month, Integer year, String title, String notes, MultipartFile file) throws IOException{
        if(month == null || month < 1 || month > 12) throw new IllegalArgumentException("Mes invalido (1..12"); 

        if(year == null || year < 2000) throw new IllegalArgumentException("Año inválido");

        if(title == null || title.trim().isEmpty()) throw new IllegalArgumentException("El título es obligatorio");
        if(file == null || file.isEmpty()) throw new IllegalArgumentException("El archivo es obligatorio");

        ReporteMensual reporte = new ReporteMensual();

        reporte.setMonth(month);
        reporte.setYear(year);
        reporte.setTitle(title);
        reporte.setNotes(notes);
        reporte.setContentType(file.getContentType());
        reporte.setFilename(file.getOriginalFilename()); 
        reporte.setData(file.getBytes());

        return repository.save(reporte); 

    }

    public List<ReporteMensual> listar(){
        return repository.findAllOrderByFecha();
    }
    
    public ReporteMensual findById(long id){
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado")); 
    }

    public void eliminar(long id){
        repository.deleteById(id);
    }
}
