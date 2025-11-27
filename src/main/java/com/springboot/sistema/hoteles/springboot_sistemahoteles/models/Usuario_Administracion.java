package com.springboot.sistema.hoteles.springboot_sistemahoteles.models;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario_administracion")
public class Usuario_Administracion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "nombres_apellidos", nullable = true)
    private String nombres_apellidos;

    @Column(name = "email")
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "rol_id")
    private Long rol_id;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha_creado")
    private LocalDate fecha_creado;

    @Column(name = "fecha_actualizacion")
    private LocalDate fecha_actualizacion;

    public Usuario_Administracion() {
    }

    public Usuario_Administracion(Long id, String nombres_apellidos, String email, String passwordHash, Long rol_id,
            String estado,
            LocalDate fecha_creado, LocalDate fecha_actualizacion) {
        this.id = id;
        this.nombres_apellidos = nombres_apellidos;
        this.email = email;
        this.passwordHash = passwordHash;
        this.rol_id = rol_id;
        this.estado = estado;
        this.fecha_creado = fecha_creado;
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres_apellidos() {
        return nombres_apellidos;
    }

    public void setNombres_apellidos(String nombres_apellidos) {
        this.nombres_apellidos = nombres_apellidos;
    }

    public String getNombres() {
        return this.nombres_apellidos;
    }

    public void setNombres(String nombres) {
        this.nombres_apellidos = nombres;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Long getRol_id() {
        return rol_id;
    }

    public void setRol_id(Long rol_id) {
        this.rol_id = rol_id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFecha_creado() {
        return fecha_creado;
    }

    public void setFecha_creado(LocalDate fecha_creado) {
        this.fecha_creado = fecha_creado;
    }

    public LocalDate getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(LocalDate fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Usuario_Administracion)) {
            return false;
        }
        Usuario_Administracion other = (Usuario_Administracion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombres de empleado=" + nombres_apellidos +
                ", email de empleado: " + email +
                ", rol del empleado: " + rol_id +
                ", estado (activo, desactivado: )" + estado +
                ", creado en: " + fecha_creado +
                ", fecha de actualización: " + fecha_actualizacion +
                '}';
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuarios_admin_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"), uniqueConstraints = @UniqueConstraint(name = "uk_usuario_rol", columnNames = {
            "usuario_id", "rol_id" }))
    private java.util.Set<com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Roles> roles = new java.util.HashSet<>();

    public java.util.Set<com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Roles> getRoles() {
        return roles;
    }

    public void setRoles(java.util.Set<com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Roles> roles) {
        this.roles = roles;
    }

    @PrePersist
    public void prePersist(){
        this.fecha_creado = LocalDate.now(); 
        this.fecha_actualizacion = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.fecha_actualizacion = LocalDate.now(); 
    }
}
