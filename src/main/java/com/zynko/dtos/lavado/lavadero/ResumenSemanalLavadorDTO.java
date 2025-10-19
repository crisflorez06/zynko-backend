package com.zynko.dtos.lavado.lavadero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumenSemanalLavadorDTO {

    private String lavador;
    private int domingo;
    private int lunes;
    private int martes;
    private int miercoles;
    private int jueves;
    private int viernes;
    private int sabado;

    public ResumenSemanalLavadorDTO(String lavador) {
        this.lavador = lavador;
    }

    public void acumular(DayOfWeek dia, int valor) {
        switch (dia) {
            case MONDAY -> this.lunes += valor;
            case TUESDAY -> this.martes += valor;
            case WEDNESDAY -> this.miercoles += valor;
            case THURSDAY -> this.jueves += valor;
            case FRIDAY -> this.viernes += valor;
            case SATURDAY -> this.sabado += valor;
            case SUNDAY -> this.domingo += valor;
        }
    }
}
