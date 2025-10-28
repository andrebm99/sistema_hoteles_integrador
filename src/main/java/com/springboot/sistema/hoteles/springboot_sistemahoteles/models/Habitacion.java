package com.springboot.sistema.hoteles.springboot_sistemahoteles.models;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "habitaciones")
public class Habitacion implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habitacion", unique = true)
    private Long id_habitacion;

    @Column(name = "numerohabitacion", unique = true, nullable = false)
    private Integer numerohabitacion; 

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

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "foto_portada", columnDefinition = "LONGBLOB")
    private byte[] foto_portada;

    @Column(name = "foto_portada_content_type")
    private String foto_portada_content_type;

    @Column(name = "foto_portada_filename")
    private String foto_portada_filename;

    @Column(name = "capacidad_total")
    private String capacidad_total;

    @Column(name = "precio")
    private Double precio; 

    public Habitacion() {
    }

    public Habitacion(Long id_habitacion, Integer numerohabitacion, String nombre_comercial, String descripcion,
            String medidas, String vista, String estado_operativo, byte[] foto_portada,
            String foto_portada_content_type, String foto_portada_filename, String capacidad_total, Double precio) {
        this.id_habitacion = id_habitacion;
        this.numerohabitacion = numerohabitacion;
        this.nombre_comercial = nombre_comercial;
        this.descripcion = descripcion;
        this.medidas = medidas;
        this.vista = vista;
        this.estado_operativo = estado_operativo;
        this.foto_portada = foto_portada;
        this.foto_portada_content_type = foto_portada_content_type;
        this.foto_portada_filename = foto_portada_filename;
        this.capacidad_total = capacidad_total;
        this.precio = precio;
    }

    public Long getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(Long id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public Integer getNumerohabitacion() {
        return numerohabitacion;
    }

    public void setNumerohabitacion(Integer numerohabitacion) {
        this.numerohabitacion = numerohabitacion;
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

    public byte[] getFoto_portada() {
        return foto_portada;
    }

    public void setFoto_portada(byte[] foto_portada) {
        this.foto_portada = foto_portada;
    }

    public String getFoto_portada_content_type() {
        return foto_portada_content_type;
    }

    public void setFoto_portada_content_type(String foto_portada_content_type) {
        this.foto_portada_content_type = foto_portada_content_type;
    }

    public String getFoto_portada_filename() {
        return foto_portada_filename;
    }

    public void setFoto_portada_filename(String foto_portada_filename) {
        this.foto_portada_filename = foto_portada_filename;
    }

    public String getCapacidad_total() {
        return capacidad_total;
    }

    public void setCapacidad_total(String capacidad_total) {
        this.capacidad_total = capacidad_total;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
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
        ", numero de habitracion: " + numerohabitacion+
        ", nombres comercial=" + nombre_comercial +
        ", descripción: " + descripcion + 
        ", medidas: " + medidas +
        ", vista: " + vista + 
        ", estado operativo: " + estado_operativo + 
        ", capacidad total: " + capacidad_total + 
        ", precio: " + precio + 
        '}';
    }
    
}
