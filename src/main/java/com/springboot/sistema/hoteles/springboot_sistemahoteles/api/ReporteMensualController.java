package com.springboot.sistema.hoteles.springboot_sistemahoteles.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.ReporteMensual;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.services.ReporteMensualService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/reportes")
public class ReporteMensualController {
    @Autowired
    private ReporteMensualService service;

    @PostMapping(value = "/subir", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> subir(
            @RequestParam("month") Integer month,
            @RequestParam("year") Integer year,
            @RequestParam("title") String title,
            @RequestParam(value = "notes", required = false) String notes,
            @RequestPart("file") MultipartFile file) {

        try {
            ReporteMensual reporte = service.subir(month, year, title, notes, file);
            Map<String, Object> resp = new HashMap<>();
            resp.put("id_reporte", reporte.getId_reporte());
            resp.put("month", reporte.getMonth());
            resp.put("year", reporte.getYear());
            resp.put("title", reporte.getTitle());
            resp.put("filename", reporte.getFilename());
            resp.put("createdAt", reporte.getCreatedAt());
            return ResponseEntity.status(HttpStatus.CREATED).body(resp);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error leyendo archivo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir reporte");
        }
    }

    @GetMapping
    public ResponseEntity<List<ReporteMensual>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @SuppressWarnings("null")
    @GetMapping("/{id}/descargar")
    public ResponseEntity<byte[]> descargar(@PathVariable("id") Long id) {
        try {
            ReporteMensual reporte = service.findById(id);
            MediaType media;

            try {
                media = reporte.getContentType() != null ? MediaType.parseMediaType(reporte.getContentType())
                        : MediaType.APPLICATION_OCTET_STREAM;
            } catch (Exception exceptiion) {
                media = MediaType.APPLICATION_OCTET_STREAM;
            }

            return ResponseEntity.ok()
                    .contentType(media)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + (reporte.getFilename() != null ? reporte.getFilename() : ("reporte_"+reporte.getYear()+"_"+reporte.getMonth())) + "\"")
                    .contentLength(reporte.getData() != null ? reporte.getData().length : 0)
                    .body(reporte.getData());
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @SuppressWarnings("null")
    @GetMapping("/{id}/ver")
    public ResponseEntity<byte[]> ver(@PathVariable("id") Long id) {
        try{
            ReporteMensual reporte = service.findById(id);
            MediaType media;

            try{
                media = reporte.getContentType() != null ?
                MediaType.parseMediaType(reporte.getContentType()) : MediaType.APPLICATION_OCTET_STREAM;
            } catch(Exception ex){
                media = MediaType.APPLICATION_OCTET_STREAM; 
            }

            return ResponseEntity.ok()
                .contentType(media)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + (reporte.getFilename() != null ?
                reporte.getFilename() : ("reporte_" + reporte.getYear() + "_" + reporte.getMonth())) + "\"").contentLength(reporte.getData() != null ? reporte.getData().length : 0)
                .body(reporte.getData()); 
        } catch(IllegalArgumentException exc){
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Long id){
        try{
            service.eliminar(id);
            return ResponseEntity.noContent().build(); 
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo eliminar."); 
        }
    }
    

}