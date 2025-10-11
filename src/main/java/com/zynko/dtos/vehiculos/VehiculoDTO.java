package com.zynko.dtos.vehiculos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDTO {

    private String placa;
    private String tipo;
    private Integer totalCobrado;
}
