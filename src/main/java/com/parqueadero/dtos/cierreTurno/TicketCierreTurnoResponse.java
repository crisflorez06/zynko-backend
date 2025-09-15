package com.parqueadero.dtos.cierreTurno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketCierreTurnoResponse {

    private long id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaCierre;
    private Integer total;
    private String nombreUsuario;
    private Map<String, DetalleParqueaderoCierre> detallesPorParqueadero;
}