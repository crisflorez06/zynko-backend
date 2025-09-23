package com.parqueadero.dtos.turnoIsla;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnoIslaResponse {

    private double gasolinaInicial1;
    private double gasolinaInicial2;
    private double gasolinaInicial3;
    private double gasolinaInicial4;
    private double dieselInicial1;
    private double dieselInicial2;
    private double dieselInicial3;
    private double dieselInicial4;
    private double gasolinaFinal1;
    private double gasolinaFinal2;
    private double gasolinaFinal3;
    private double gasolinaFinal4;
    private double dieselFinal1;
    private double dieselFinal2;
    private double dieselFinal3;
    private double dieselFinal4;
    private Integer totalVentas;
    private Integer TotalTiros;
    private Integer totalCreditos;
}