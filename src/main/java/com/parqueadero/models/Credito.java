package com.parqueadero.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "credito")
public class Credito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "credito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreditoProducto> items;
    private Integer total;
    private LocalDateTime fecha;
    private  String placa;

    //esta propiedad se creo con el fin de que haya una manera de encontrar los registros de un turno facilmente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turno_isla_id")
    @JsonIgnore
    private TurnoIsla turnoIsla;

    public Credito() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<CreditoProducto> getItems() {
        return items;
    }

    public void setItems(List<CreditoProducto> items) {
        this.items = items;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
