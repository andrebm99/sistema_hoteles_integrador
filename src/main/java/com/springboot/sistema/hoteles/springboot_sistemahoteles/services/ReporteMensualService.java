package com.springboot.sistema.hoteles.springboot_sistemahoteles.services;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.ReporteMensual;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.ReporteMensualRepository;

import jakarta.transaction.Transactional;

@Service
public class ReporteMensualService {
    private final ReporteMensualRepository repository;

    private static final Pattern URL_PATTERN = Pattern.compile("^(https?://).+");

    public ReporteMensualService(ReporteMensualRepository repository) {
        this.repository = repository;
    }

    public ReporteMensual subir(Integer month, Integer year, String title, String notes, String urlFormulario) {
        if (month == null || month < 1 || month > 12)
            throw new IllegalArgumentException("Mes inválido (1..12)");
        if (year == null || year < 2000)
            throw new IllegalArgumentException("Año inválido");
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("El título es obligatorio");
        if (urlFormulario == null || urlFormulario.trim().isEmpty())
            throw new IllegalArgumentException("La URL del formulario es obligatoria");
        if (!URL_PATTERN.matcher(urlFormulario.trim()).matches())
            throw new IllegalArgumentException("La URL debe iniciar con http:// o https://");

        ReporteMensual reporte = new ReporteMensual();
        reporte.setMonth(month);
        reporte.setYear(year);
        reporte.setTitle(title.trim());
        reporte.setNotes(notes);
        reporte.setUrlFormulario(urlFormulario.trim());
        return repository.save(reporte);
    }

    public List<ReporteMensual> listar() {
        return repository.findAllOrderByFecha();
    }

    public ReporteMensual findById(long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado"));
    }

    @Transactional
    public void eliminar(long id) {
        repository.deleteById(id);
    }

    @Transactional
    public ReporteMensual editar(long id, Integer month, Integer year, String title, String notes,
            String urlFormulario) {
        if (month == null || month < 1 || month > 12)
            throw new IllegalArgumentException("Mes inválido (1..12)");
        if (year == null || year < 2000)
            throw new IllegalArgumentException("Año inválido");
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("El título es obligatorio");
        if (urlFormulario == null || urlFormulario.trim().isEmpty())
            throw new IllegalArgumentException("La URL del formulario es obligatoria");

        ReporteMensual existente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado"));

        existente.setMonth(month);
        existente.setYear(year);
        existente.setTitle(title.trim());
        existente.setNotes(notes);
        existente.setUrlFormulario(urlFormulario.trim());

        return repository.save(existente);
    }

}
