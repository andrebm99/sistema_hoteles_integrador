package com.springboot.sistema.hoteles.springboot_sistemahoteles.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "reservas")
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long id_reserva;

    @Column(name = "nombres_apellidos", nullable = true)
    private String nombres_apellidos;

    @Column(name = "dni", unique = true, nullable = true)
    private Integer dni;

    @Column(name = "habitacion", nullable = true)
    private String habitacion;

    @Column(name = "piso", nullable = true)
    private Integer piso;

    @Column(name = "fecha_inicio", nullable = true)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_salida")
    private LocalDateTime fecha_salida;

    @Column(name = "metodo_pago")
    private String metodo_pago;

    public Reserva() {
    }

    public Reserva(Long id_reserva, String nombres_apellidos, Integer dni, String habitacion, Integer piso, LocalDateTime fechaInicio,
            LocalDateTime fecha_salida, String metodo_pago) {
        this.id_reserva = id_reserva;
        this.nombres_apellidos = nombres_apellidos;
        this.dni = dni;
        this.habitacion = habitacion;
        this.piso = piso;
        this.fechaInicio = fechaInicio;
        this.fecha_salida = fecha_salida;
        this.metodo_pago = metodo_pago;
    }

    public Long getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(Long id_reserva) {
        this.id_reserva = id_reserva;
    }

    public String getNombres_apellidos() {
        return nombres_apellidos;
    }

    public void setNombres_apellidos(String nombres_apellidos) {
        this.nombres_apellidos = nombres_apellidos;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public Integer getPiso() {
        return piso;
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(LocalDateTime fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public String getMetodo_pago() {
        return metodo_pago;
    }

    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_reserva != null ? id_reserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.id_reserva == null && other.id_reserva != null) || (this.id_reserva != null && !this.id_reserva.equals(other.id_reserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reserva{" + 
        "id=" + id_reserva + 
        ", nombres=" + nombres_apellidos 
        + ", dni: " + dni + 
        ", habitación: " + habitacion + 
        ", piso: " + piso + 
        ", fecha de inicio: " + fechaInicio + 
        ", fecha de salida: " + fecha_salida + 
        ", metodo de pago: " + metodo_pago + 
        '}';
    }
}
