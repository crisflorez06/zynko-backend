package com.parqueadero.dtos.creditos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditoRequest {

    private Long clienteId;
    private List<CreditoProductoRequest> items;
    private String placa;


}
