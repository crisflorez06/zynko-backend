package com.zynko.dtos.parqueadero.ticketsParqueadero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketSalidaRequest {

    private String codigo;
    private long idUsuarioLogueado;
}