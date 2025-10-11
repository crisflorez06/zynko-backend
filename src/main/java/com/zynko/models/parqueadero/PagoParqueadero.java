package com.zynko.models.parqueadero;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagos")
public class PagoParqueadero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean estado;
    private Integer total;
    private LocalDateTime fechaHora;

    @OneToOne
    @JoinColumn(name = "ticket_id")
    @JsonIgnore
    private TicketParqueadero ticketParqueadero;

    public PagoParqueadero() {
    }

    public PagoParqueadero(Boolean estado, Integer total, LocalDateTime fechaHora, TicketParqueadero ticketParqueadero) {
        this.estado = estado;
        this.total = total;
        this.fechaHora = fechaHora;
        this.ticketParqueadero = ticketParqueadero;
    }

    public PagoParqueadero(Long id, Boolean estado, Integer total, LocalDateTime fechaHora, TicketParqueadero ticketParqueadero) {
        this.id = id;
        this.total = total;
        this.fechaHora = fechaHora;
        this.ticketParqueadero = ticketParqueadero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public TicketParqueadero getTicket() {
        return ticketParqueadero;
    }

    public void setTicket(TicketParqueadero ticketParqueadero) {
        this.ticketParqueadero = ticketParqueadero;
    }
    
}
