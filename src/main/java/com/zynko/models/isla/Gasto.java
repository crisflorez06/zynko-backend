package com.zynko.models.isla;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "gasto")
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer cantidad;
    private LocalDateTime fecha;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turno_isla_id")
    @JsonIgnore
    private TurnoIsla turnoIsla;

    public Gasto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public TurnoIsla getTurnoIsla() {
        return turnoIsla;
    }

    public void setTurnoIsla(TurnoIsla turnoIsla) {
        this.turnoIsla = turnoIsla;
    }
}
