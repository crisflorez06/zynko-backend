package com.zynko.dtos.lavado.lavadero;

import com.zynko.dtos.vehiculos.VehiculoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumenLavadorDTO {

    private String lavador;
    private int totalRecaudado;
    private int paraEstacion;
    private int paraLavador;
    private List<VehiculoDTO> vehiculos;
}
