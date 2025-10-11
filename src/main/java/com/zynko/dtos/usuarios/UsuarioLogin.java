package com.zynko.dtos.usuarios;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioLogin {

    private String nombre;
    private String cedula;
}