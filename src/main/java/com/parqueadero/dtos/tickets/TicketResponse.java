package com.parqueadero.dtos.tickets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {

    private String codigo;
    private String placa;
    private String tipoVehiculo;
    private LocalDateTime fechaHoraEntrada;
    private LocalDateTime fechaHoraSalida;
    private Boolean estadoPago;
    private Integer totalPagar;
    private String usuarioRecibio;
    private String usuarioEntrego;
    private  String parqueadero;
}