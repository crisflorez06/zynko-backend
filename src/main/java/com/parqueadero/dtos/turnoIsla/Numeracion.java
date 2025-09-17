package com.parqueadero.dtos.turnoIsla;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Numeracion {

    private double gasolina1;
    private double gasolina2;
    private double gasolina3;
    private double gasolina4;
    private double diesel1;
    private double diesel2;
    private double diesel3;
    private double diesel4;

    public double getTotalGasolina() {
        return gasolina1 + gasolina2 + gasolina3 + gasolina4;
    }

    public double getTotalDiesel() {
        return diesel1 + diesel2 + diesel3 + diesel4;
    }
}