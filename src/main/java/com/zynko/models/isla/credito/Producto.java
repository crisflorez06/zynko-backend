package com.zynko.models.isla.credito;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

//importar las clases de jakarta persistence para mapear la clase a la base de datos
import jakarta.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;



    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private List<CreditoProducto> ventasCredito;

    public Producto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}