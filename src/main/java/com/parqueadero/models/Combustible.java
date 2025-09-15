package com.parqueadero.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "combustible")
public class Combustible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "combustible_id")
    private List<Manguera> mangueras;
    
    private Integer precioVenta;
    private Integer totalDinero;

    public Combustible() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Manguera> getMangueras() {
        return mangueras;
    }

    public void setMangueras(List<Manguera> mangueras) {
        this.mangueras = mangueras;
    }

    public Integer getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Integer precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Integer getTotalDinero() {
        return totalDinero;
    }

    public void setTotalDinero(Integer totalDinero) {
        this.totalDinero = totalDinero;
    }
}
