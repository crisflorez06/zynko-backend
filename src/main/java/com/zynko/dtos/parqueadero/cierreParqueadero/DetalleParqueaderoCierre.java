package com.zynko.dtos.parqueadero.cierreParqueadero;

import com.zynko.dtos.vehiculos.TotalVehiculosDTO;
import com.zynko.dtos.vehiculos.VehiculoDTO;
import com.zynko.models.Vehiculo;
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
