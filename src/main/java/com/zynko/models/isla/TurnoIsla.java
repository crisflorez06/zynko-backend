package com.zynko.models.isla;

import com.zynko.models.Usuario;
import com.zynko.models.isla.credito.Credito;
import com.zynko.models.lavadero.Lavado;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "turno_isla")
public class TurnoIsla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private boolean activo;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFinal;
    private Double totalGalonesGasolina = 0.0;
    private Double totalGalonesDiesel = 0.0;
    private Double totalVentaGasolina = 0.0;
    private Double totalVentaDiesel = 0.0;


    @Column(name = "numeracion_inicial", columnDefinition = "TEXT")
    private String numeracionInicial;

    @Column(name = "numeracion_final", columnDefinition = "TEXT")
    private String numeracionFinal;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "turno_isla_id")
    private List<Tiro> tiros;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "turno_isla_id")
    private List<Credito> creditos;

    private Integer visas;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "turno_isla_id")
    private List<Gasto> gastos;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "turno_isla_id")
    private List<Lavado> lavados = new ArrayList<>();

    private Integer total;

    private Integer cuadre;

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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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

    public String getNumeracionInicial() {
        return numeracionInicial;
    }

    public void setNumeracionInicial(String numeracionInicial) {
        this.numeracionInicial = numeracionInicial;
    }

    public String getNumeracionFinal() {
        return numeracionFinal;
    }

    public void setNumeracionFinal(String numeracionFinal) {
        this.numeracionFinal = numeracionFinal;
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

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    public List<Lavado> getLavados() {
        return lavados;
    }

    public void setLavados(List<Lavado> lavados) {
        this.lavados = lavados;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getTotalGalonesGasolina() {
        return totalGalonesGasolina;
    }

    public void setTotalGalonesGasolina(Double totalGalonesGasolina) {
        this.totalGalonesGasolina = totalGalonesGasolina;
    }

    public Double getTotalGalonesDiesel() {
        return totalGalonesDiesel;
    }

    public void setTotalGalonesDiesel(Double totalGalonesDiesel) {
        this.totalGalonesDiesel = totalGalonesDiesel;
    }

    public Double getTotalVentaGasolina() {
        return totalVentaGasolina;
    }

    public void setTotalVentaGasolina(Double totalVentaGasolina) {
        this.totalVentaGasolina = totalVentaGasolina;
    }

    public Double getTotalVentaDiesel() {
        return totalVentaDiesel;
    }

    public void setTotalVentaDiesel(Double totalVentaDiesel) {
        this.totalVentaDiesel = totalVentaDiesel;
    }

    public Integer getCuadre() {
        return cuadre;
    }

    public void setCuadre(Integer cuadre) {
        this.cuadre = cuadre;
    }


    @PrePersist
    public void prePersist() {
        this.cuadre = 0;
        this.visas = 0;
        this.total = 0;
        this.numeracionFinal = this.numeracionInicial;
    }

}
