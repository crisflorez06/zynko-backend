package com.parqueadero.dtos.lavadero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumenLavaderoResponse {

    private long totalLavados;
    private int totalRecaudado;
    private int totalEstacion;
    private int totalLavadores;
    private int totalPendiente;
    private List<ResumenLavadorDTO> detalleLavadores;
}
