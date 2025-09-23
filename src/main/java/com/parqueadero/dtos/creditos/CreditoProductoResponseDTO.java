package com.parqueadero.dtos.creditos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditoProductoResponseDTO {
    private Long id;
    private ProductoDTO producto;
    private Integer precioVenta;
}
