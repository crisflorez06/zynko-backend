package com.zynko.dtos.parqueadero.cierreParqueadero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketCierreParqueaderoResponse {

    private long id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaCierre;
    private Integer total;
    private String nombreUsuario;
    private Map<String, DetalleParqueaderoCierre> detallesPorParqueadero;
}