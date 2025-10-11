package com.parqueadero.dtos.lavador;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LavadorResponse {
    private Long id;
    private String nombre;
}
