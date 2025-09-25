package com.parqueadero.dtos.gastos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GastoRequest {
    private String nombre;
    private Integer cantidad;
}
