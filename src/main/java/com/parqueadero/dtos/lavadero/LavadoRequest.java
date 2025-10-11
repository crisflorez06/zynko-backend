package com.parqueadero.dtos.lavadero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LavadoRequest {

    private String placa;
    private String tipoVehiculo;
    private String lavador;
    private Long lavadorId;
    private Integer valorTotal;
    private boolean pagado;
}
