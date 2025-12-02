package com.springboot.sistema.hoteles.springboot_sistemahoteles.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.GenerationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="reportes_mensuales")
public class ReporteMensual implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reporte")
    private Long id_reporte;

    @Column(name = "month", nullable = false)
    private Integer month;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name ="notes")
    private String notes; 

    @Column(name = "url_formulario")
    private String urlFormulario; 

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; 

    @PrePersist
    public void prePersist(){
        if(createdAt == null) createdAt = LocalDateTime.now(); 
    }

    public ReporteMensual() {
    }

    public ReporteMensual(Long id_reporte, Integer month, Integer year, String title, String notes,
            String urlFormulario, LocalDateTime createdAt) {
        this.id_reporte = id_reporte;
        this.month = month;
        this.year = year;
        this.title = title;
        this.notes = notes;
        this.urlFormulario = urlFormulario;
        this.createdAt = createdAt;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId_reporte() {
        return id_reporte;
    }

    public void setId_reporte(Long id_reporte) {
        this.id_reporte = id_reporte;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUrlFormulario() {
        return urlFormulario;
    }

    public void setUrlFormulario(String urlFormulario) {
        this.urlFormulario = urlFormulario;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
