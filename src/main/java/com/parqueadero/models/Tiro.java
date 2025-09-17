package com.parqueadero.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "tiro")
public class Tiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long numero;
    private Integer cantidad;
    private LocalDateTime fecha;

    @ElementCollection
    @CollectionTable(name = "tiro_tipo_turno", joinColumns = @JoinColumn(name = "tiro_id"))
    @MapKeyColumn(name = "tiro_tipo_dia")
    @Column(name = "tiro_tipo_hora")
    private Map<Integer, String> tipoTurno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turno_isla_id")
    @JsonIgnore
    private TurnoIsla turnoIsla;

    public Tiro() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Map<Integer, String> getTipoTurno() {
        return tipoTurno;
    }

    public void setTipoTurno(Map<Integer, String> tipoTurno) {
        this.tipoTurno = tipoTurno;
    }

    public TurnoIsla getTurnoIsla() {
        return turnoIsla;
    }

    public void setTurnoIsla(TurnoIsla turnoIsla) {
        this.turnoIsla = turnoIsla;
    }
}
