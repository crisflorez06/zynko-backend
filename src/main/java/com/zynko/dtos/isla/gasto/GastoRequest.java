package com.zynko.dtos.isla.gasto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GastoRequest {
    private String nombre;
    private Integer cantidad;
}
