package com.springboot.sistema.hoteles.springboot_sistemahoteles.models;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Roles implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", unique = true)
    private Long id_rol;

    @Column(name = "nombre_rol", nullable = true)
    private String nombre_rol;

    @Column(name = "descripcion")
    private String descripcion; 

    public Roles() {
    }

    public Roles(Long id_rol, String nombre_rol, String descripcion) {
        this.id_rol = id_rol;
        this.nombre_rol = nombre_rol;
        this.descripcion = descripcion;
    }
    
    public Long getId_rol() {
        return id_rol;
    }

    public void setId_rol(Long id_rol) {
        this.id_rol = id_rol;
    }

    public String getNombre_rol() {
        return nombre_rol;
    }

    public void setNombre_rol(String nombre_rol) {
        this.nombre_rol = nombre_rol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_rol != null ? id_rol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Roles)) {
            return false;
        }
        Roles other = (Roles) object;
        if ((this.id_rol == null && other.id_rol != null) || (this.id_rol != null && !this.id_rol.equals(other.id_rol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Roles{" + 
        "id=" + id_rol + 
        ", nombres de rol=" + nombre_rol 
        + ", descripción: " + descripcion + 
        '}';
    }
    
}
