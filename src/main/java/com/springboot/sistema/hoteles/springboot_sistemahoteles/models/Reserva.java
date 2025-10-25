package com.springboot.sistema.hoteles.springboot_sistemahoteles.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "reservas")
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long id_reserva;

    @Column(name = "codigo", unique = true, nullable = false, length = 36)
    private String codigo;

    @Column(name = "nombresapellidos", nullable = false)
    private String nombresapellidos;

    @Column(name = "dni", unique = true, nullable = false)
    private Integer dni;

    @Column(name = "habitacion", nullable = false)
    private String habitacion;

    @Column(name = "ocupantes", nullable = false)
    private String ocupantes;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_salida")
    private LocalDateTime fecha_salida;

    @Column(name = "metodo_pago")
    private String metodo_pago;

    public Reserva() {
    }

    public Reserva(Long id_reserva, String codigo, String nombresapellidos, Integer dni, String habitacion,
            String ocupantes, LocalDateTime fechaInicio, LocalDateTime fecha_salida, String metodo_pago) {
        this.id_reserva = id_reserva;
        this.nombresapellidos = nombresapellidos;
        this.dni = dni;
        this.habitacion = habitacion;
        this.ocupantes = ocupantes;
        this.fechaInicio = fechaInicio;
        this.fecha_salida = fecha_salida;
        this.metodo_pago = metodo_pago;
    }

    @PrePersist
    public void prePersist(){
        if(this.codigo == null || this.codigo.isEmpty()){
            this.codigo = UUID.randomUUID().toString(); 
        }
    }
    
    public Long getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(Long id_reserva) {
        this.id_reserva = id_reserva;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombresapellidos() {
        return nombresapellidos;
    }

    public void setNombresapellidos(String nombresapellidos) {
        this.nombresapellidos = nombresapellidos;
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

    public String getOcupantes() {
        return ocupantes;
    }

    public void setOcupantes(String ocupantes) {
        this.ocupantes = ocupantes;
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
        "id=" + codigo +
        ", nombres=" + nombresapellidos +
        ", dni: " + dni + 
        ", habitación: " + habitacion + 
        ", Cantidad de Ocupantes: " + ocupantes + 
        ", fecha de inicio: " + fechaInicio + 
        ", fecha de salida: " + fecha_salida + 
        ", metodo de pago: " + metodo_pago + 
        '}';
    }
}
