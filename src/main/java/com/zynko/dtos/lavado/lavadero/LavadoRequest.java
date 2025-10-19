package com.zynko.dtos.lavado.lavadero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LavadoRequest {

    private String placa;
    private String tipoVehiculo;
    private Long lavadorId;
    private Integer valorTotal;
    private Boolean pagado;
}
