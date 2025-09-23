package com.parqueadero.dtos.creditos;

import com.parqueadero.models.Cliente;
import com.parqueadero.models.Producto;
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
