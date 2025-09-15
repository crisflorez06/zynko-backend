package com.parqueadero.dtos.vehiculos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalVehiculosDTO {
    private String tipo;
    private Long cantidad;
}