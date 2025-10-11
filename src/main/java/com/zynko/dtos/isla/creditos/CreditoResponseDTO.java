package com.zynko.dtos.isla.creditos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditoResponseDTO {
    private Long id;
    private String cliente;
    private long idCliente;
    private Integer total;
    private LocalDateTime fecha;
    private List<CreditoProductoResponseDTO> items;
    private String placa;
}
