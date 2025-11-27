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

    @Column(name = "dni", nullable = false)
    private Integer dni;

    @Column(name = "edad", nullable = false)
    private Integer edad;

    @Column(name = "habitacion", nullable = false)
    private String habitacion;

    @Column(name = "id_habitacion")
    private Long id_habitacion;

    @Column(name = "ocupantes", nullable = false)
    private String ocupantes;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_salida")
    private LocalDateTime fecha_salida;

    @Column(name = "precio_aplicado")
    private Double precio_aplicado;

    @Column(name = "total_aplicado")
    private Double total_aplicado;

    @Column(name = "metodo_pago")
    private String metodo_pago;

    public Reserva() {
    }

    public Reserva(Long id_reserva, String codigo, String nombresapellidos, Integer dni, Integer edad,
            String habitacion, Long id_habitacion, String ocupantes, LocalDateTime fechaInicio,
            LocalDateTime fecha_salida, Double precio_aplicado, Double total_aplicado, String metodo_pago) {
        this.id_reserva = id_reserva;
        this.codigo = codigo;
        this.nombresapellidos = nombresapellidos;
        this.dni = dni;
        this.edad = edad;
        this.habitacion = habitacion;
        this.id_habitacion = id_habitacion;
        this.ocupantes = ocupantes;
        this.fechaInicio = fechaInicio;
        this.fecha_salida = fecha_salida;
        this.precio_aplicado = precio_aplicado;
        this.total_aplicado = total_aplicado;
        this.metodo_pago = metodo_pago;
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

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public Long getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(Long id_habitacion) {
        this.id_habitacion = id_habitacion;
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

    public Double getPrecio_aplicado() {
        return precio_aplicado;
    }

    public void setPrecio_aplicado(Double precio_aplicado) {
        this.precio_aplicado = precio_aplicado;
    }

    public Double getTotal_aplicado() {
        return total_aplicado;
    }

    public void setTotal_aplicado(Double total_aplicado) {
        this.total_aplicado = total_aplicado;
    }

    public String getMetodo_pago() {
        return metodo_pago;
    }

    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    @PrePersist
    public void prePersist() {
        if (this.codigo == null || this.codigo.isEmpty()) {
            this.codigo = generarCodigoReserva();
        }
    }

    private String generarCodigoReserva() {
        String letras = UUID.randomUUID().toString().replace("-", "").substring(0, 3).toUpperCase();
        int numeros = (int) (Math.random() * 900) + 100;
        return "RES-" + letras + numeros;
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
        if ((this.id_reserva == null && other.id_reserva != null)
                || (this.id_reserva != null && !this.id_reserva.equals(other.id_reserva))) {
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
