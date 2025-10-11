package com.zynko.dtos.parqueadero.ticketsParqueadero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketMensualidadRequest {

    private LocalDateTime fechaHoraEntrada;
    private long usuarioId;
    private String placa;
    private String tipoVehiculo;
    private String parqueadero;
    private int dias;
    private Integer total;
}