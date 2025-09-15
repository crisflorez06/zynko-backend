package com.parqueadero.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
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

    @ElementCollection
    @CollectionTable(name = "credito_productos", joinColumns = @JoinColumn(name = "credito_id"))
    @MapKeyColumn(name = "producto_nombre")
    @Column(name = "producto_cantidad")
    private Map<String, Integer> productos;

    private Integer total;
    private LocalDateTime fecha;
    private String placa;

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

    public Map<String, Integer> getProductos() {
        return productos;
    }

    public void setProductos(Map<String, Integer> productos) {
        this.productos = productos;
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

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
