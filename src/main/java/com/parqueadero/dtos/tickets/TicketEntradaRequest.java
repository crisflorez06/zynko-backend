package com.parqueadero.dtos.tickets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntradaRequest {

    private String placa;
    private String tipoVehiculo;
    private long usuarioRecibioId;
    private String parqueadero;
}