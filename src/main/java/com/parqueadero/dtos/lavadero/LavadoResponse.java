package com.parqueadero.dtos.lavadero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LavadoResponse {

    private Long id;
    private String placa;
    private String tipoVehiculo;
    private String lavador;
    private Long lavadorId;
    private Integer valorTotal;
    private boolean pagado;
    private LocalDateTime fechaRegistro;
}
