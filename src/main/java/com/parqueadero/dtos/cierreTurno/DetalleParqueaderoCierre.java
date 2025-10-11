package com.parqueadero.dtos.cierreTurno;

import com.parqueadero.dtos.vehiculos.TotalVehiculosDTO;
import com.parqueadero.dtos.vehiculos.VehiculoDTO;
import com.parqueadero.models.Vehiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleParqueaderoCierre {

    private List<Vehiculo> listaVehiculosEntrantes;
    private List<VehiculoDTO> listaVehiculosSalientes;
    private Integer totalAPagar;
    private List<VehiculoDTO> vehiculosMensualidad;
    private List<Vehiculo> vehiculosEnParqueadero;
    private List<TotalVehiculosDTO> listaTiposVehiculosEntrantes;
    private List<TotalVehiculosDTO> listaTiposVehiculosSalientes;
    private List<TotalVehiculosDTO> listaTiposVehiculosParqueadero;
}
