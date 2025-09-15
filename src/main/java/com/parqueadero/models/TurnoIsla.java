package com.parqueadero.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "turno_isla")
public class TurnoIsla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFinal;

    @ManyToOne
    @JoinColumn(name = "corriente_id")
    private Combustible corriente;

    @ManyToOne
    @JoinColumn(name = "diesel_id")
    private Combustible diesel;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "turno_isla_id")
    private List<Tiro> tiros;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "turno_isla_id")
    private List<Credito> creditos;

    private Integer visas;

    @ElementCollection
    @CollectionTable(name = "turno_isla_gastos", joinColumns = @JoinColumn(name = "turno_isla_id"))
    @MapKeyColumn(name = "gasto_nombre")
    @Column(name = "gasto_valor")
    private Map<String, Integer> gastos;

    private Integer total;

    public TurnoIsla() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDateTime fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Combustible getCorriente() {
        return corriente;
    }

    public void setCorriente(Combustible corriente) {
        this.corriente = corriente;
    }

    public Combustible getDiesel() {
        return diesel;
    }

    public void setDiesel(Combustible diesel) {
        this.diesel = diesel;
    }

    public List<Tiro> getTiros() {
        return tiros;
    }

    public void setTiros(List<Tiro> tiros) {
        this.tiros = tiros;
    }

    public List<Credito> getCreditos() {
        return creditos;
    }

    public void setCreditos(List<Credito> creditos) {
        this.creditos = creditos;
    }

    public Integer getVisas() {
        return visas;
    }

    public void setVisas(Integer visas) {
        this.visas = visas;
    }

    public Map<String, Integer> getGastos() {
        return gastos;
    }

    public void setGastos(Map<String, Integer> gastos) {
        this.gastos = gastos;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
