package com.zynko.models.lavadero;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zynko.models.isla.TurnoIsla;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lavado")
public class Lavado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placa;

    @Column(name = "tipo_vehiculo")
    private String tipoVehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "lavador_id")
    private Lavador lavadorEntity;

    @Column(name = "valor_total")
    private Integer valorTotal;

    /**
     * Representa el estado del lavado.
     * false = DEBE (pendiente de pago)
     * true = PAGO (cancelado)
     */
    @Column(name = "pagado")
    private boolean pagado;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turno_isla_id")
    @JsonIgnore
    private TurnoIsla turnoIsla;

    public Lavado() {
    }

    @PrePersist
    public void prePersist() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public Lavador getLavadorEntity() {
        return lavadorEntity;
    }

    public void setLavadorEntity(Lavador lavadorEntity) {
        this.lavadorEntity = lavadorEntity;
    }

    public Integer getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Integer valorTotal) {
        this.valorTotal = valorTotal;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public TurnoIsla getTurnoIsla() {
        return turnoIsla;
    }

    public void setTurnoIsla(TurnoIsla turnoIsla) {
        this.turnoIsla = turnoIsla;
    }
}
