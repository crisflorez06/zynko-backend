package com.zynko.dtos.isla.creditos;

import com.zynko.models.isla.credito.Cliente;
import com.zynko.models.isla.credito.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditoFormularioDTO {

    private List<Cliente> clientes;
    private List<Producto> productos;
}
