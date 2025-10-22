package com.springboot.sistema.hoteles.springboot_sistemahoteles.models;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "habitaciones")
public class Habitacion implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", unique = true)
    private Long id_habitacion;

    @Column(name = "numero_habitacion", unique = true, nullable = false)
    private Integer numero_habitacion; 

    @Column(name = "nombre_comercial")
    private String nombre_comercial;

    @Column(name = "descripcion_corta")
    private String descripcion; 

    @Column(name = "medidas")
    private String medidas; 

    @Column(name = "vista")
    private String vista; 

    @Column(name = "estado_operativo")
    private String estado_operativo; 

    @Column(name = "foto_portada_url", length = 2048)
    private String foto_portada_url;

    @Column(name = "capacidad_total")
    private String capacidad_total;

    public Habitacion() {
    }

    public Habitacion(Long id_habitacion, Integer numero_habitacion, String nombre_comercial, String descripcion,
            String medidas, String vista, String estado_operativo, String foto_portada_url, String capacidad_total) {
        this.id_habitacion = id_habitacion;
        this.numero_habitacion = numero_habitacion;
        this.nombre_comercial = nombre_comercial;
        this.descripcion = descripcion;
        this.medidas = medidas;
        this.vista = vista;
        this.estado_operativo = estado_operativo;
        this.foto_portada_url = foto_portada_url;
        this.capacidad_total = capacidad_total;
    }
  
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(Long id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public Integer getNumero_habitacion() {
        return numero_habitacion;
    }

    public void setNumero_habitacion(Integer numero_habitacion) {
        this.numero_habitacion = numero_habitacion;
    }

    public String getNombre_comercial() {
        return nombre_comercial;
    }

    public void setNombre_comercial(String nombre_comercial) {
        this.nombre_comercial = nombre_comercial;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMedidas() {
        return medidas;
    }

    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }

    public String getVista() {
        return vista;
    }

    public void setVista(String vista) {
        this.vista = vista;
    }

    public String getEstado_operativo() {
        return estado_operativo;
    }

    public void setEstado_operativo(String estado_operativo) {
        this.estado_operativo = estado_operativo;
    }

    public String getFoto_portada_url() {
        return foto_portada_url;
    }

    public void setFoto_portada_url(String foto_portada_url) {
        this.foto_portada_url = foto_portada_url;
    }

    public String getCapacidad_total() {
        return capacidad_total;
    }

    public void setCapacidad_total(String capacidad_total) {
        this.capacidad_total = capacidad_total;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_habitacion != null ? id_habitacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Habitacion)) {
            return false;
        }
        Habitacion other = (Habitacion) object;
        if ((this.id_habitacion == null && other.id_habitacion != null) || (this.id_habitacion != null && !this.id_habitacion.equals(other.id_habitacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Habitación{" + 
        "id de habitación =" + id_habitacion + 
        ", nombres comercial=" + nombre_comercial +
        ", descripción: " + descripcion + 
        ", medidas: " + medidas +
        ", vista: " + vista + 
        ", estado operativo: " + estado_operativo + 
        ", foto_portada_url: " + foto_portada_url +
        '}';
    }
    
}
