package com.zynko.dtos.isla.creditos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditoProductoRequest {
    private Long productoId;
    private Integer precioVenta;
}
