package com.springboot.sistema.hoteles.springboot_sistemahoteles.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    public ReporteMensualController(ReporteMensualService service) {
        this.service = service;
    }

    @PostMapping("/subir")
    public ResponseEntity<?> subir(
            @RequestParam("month") Integer month,
            @RequestParam("year") Integer year,
            @RequestParam("title") String title,
            @RequestParam(value = "notes", required = false) String notes,
            @RequestParam("urlFormulario") String urlFormulario) {

        try {
            ReporteMensual reporte = service.subir(month, year, title, notes, urlFormulario);
            Map<String, Object> resp = new HashMap<>();
            resp.put("id_reporte", reporte.getId_reporte());
            resp.put("month", reporte.getMonth());
            resp.put("year", reporte.getYear());
            resp.put("title", reporte.getTitle());
            resp.put("urlFormulario", reporte.getUrlFormulario());
            resp.put("createdAt", reporte.getCreatedAt());
            return ResponseEntity.status(HttpStatus.CREATED).body(resp);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir reporte");
        }
    }

    @PostMapping("/{id}/editar")
    public ResponseEntity<?> editar(
            @PathVariable("id") Long id,
            @RequestParam("month") Integer month,
            @RequestParam("year") Integer year,
            @RequestParam("title") String title,
            @RequestParam(value = "notes", required = false) String notes,
            @RequestParam("urlFormulario") String urlFormulario) {

        try {
            ReporteMensual actualizado = service.editar(id, month, year, title, notes, urlFormulario);
            Map<String, Object> resp = new HashMap<>();
            resp.put("id_reporte", actualizado.getId_reporte());
            resp.put("month", actualizado.getMonth());
            resp.put("year", actualizado.getYear());
            resp.put("title", actualizado.getTitle());
            resp.put("urlFormulario", actualizado.getUrlFormulario());
            resp.put("createdAt", actualizado.getCreatedAt());
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al editar reporte");
        }
    }

    @GetMapping
    public ResponseEntity<List<ReporteMensual>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}/ver")
    public ResponseEntity<Void> ver(@PathVariable("id") Long id) {
        try {
            ReporteMensual reporte = service.findById(id);
            String url = reporte.getUrlFormulario();

            if (url == null || url.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, url)
                    .build();
        } catch (IllegalArgumentException exc) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo eliminar.");
        }
    }

}