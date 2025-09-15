package com.parqueadero.models;

import jakarta.persistence.*;

@Entity
@Table(name = "manguera")
public class Manguera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private Double lecturaInicial;
    private Double lecturaFinal;
    private Double totalGalones;

    public Manguera() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getLecturaInicial() {
        return lecturaInicial;
    }

    public void setLecturaInicial(Double lecturaInicial) {
        this.lecturaInicial = lecturaInicial;
    }

    public Double getLecturaFinal() {
        return lecturaFinal;
    }

    public void setLecturaFinal(Double lecturaFinal) {
        this.lecturaFinal = lecturaFinal;
    }

    public Double getTotalGalones() {
        return totalGalones;
    }

    public void setTotalGalones(Double totalGalones) {
        this.totalGalones = totalGalones;
    }
}
