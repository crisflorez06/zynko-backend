package com.parqueadero.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "ajustes")
public class Ajustes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer precioVentaGasolina;
    private Integer precioVentaDiesel;

    public Ajustes() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrecioVentaGasolina() {
        return precioVentaGasolina;
    }

    public void setPrecioVentaGasolina(Integer precioVentaGasolina) {
        this.precioVentaGasolina = precioVentaGasolina;
    }

    public Integer getPrecioVentaDiesel() {
        return precioVentaDiesel;
    }

    public void setPrecioVentaDiesel(Integer precioVentaDiesel) {
        this.precioVentaDiesel = precioVentaDiesel;
    }
}
