package com.parqueadero.dtos.turnoIsla;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnoIslaResponse {

    private double gasolina1;
    private double gasolina2;
    private double gasolina3;
    private double gasolina4;
    private double diesel1;
    private double diesel2;
    private double diesel3;
    private double diesel4;
    private Integer totalVentas;
    private Integer TotalTiros;
}