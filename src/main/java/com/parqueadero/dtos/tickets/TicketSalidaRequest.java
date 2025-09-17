package com.parqueadero.dtos.tickets;

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