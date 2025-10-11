package com.zynko.dtos.isla.creditos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditoRequest {

    private Long clienteId;
    private List<CreditoProductoRequest> items;
    private String placa;


}
