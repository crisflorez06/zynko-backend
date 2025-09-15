package com.parqueadero.dtos.vehiculos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoCierreDTO {

    private String placa;
    private String tipo;
    private Integer totalCobrado;
}