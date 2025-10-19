package com.zynko.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zynko.dtos.filtros.FiltrosDTO;
import com.zynko.repositories.lavadero.LavadorRepository;
import com.zynko.repositories.parqueadero.LugarParqueaderoRepository;
import com.zynko.repositories.parqueadero.TarifaParqueaderoRepository;
import com.zynko.repositories.UsuarioRepository;

@Service
public class FiltroService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TarifaParqueaderoRepository tarifaRepository;

    @Autowired
    private LugarParqueaderoRepository lugarParqueaderoRepository;

    @Autowired
    private LavadorRepository lavadorRepository;

    public FiltrosDTO getFiltros() {
        List<String> usuarios = usuarioRepository.findDistinctNombres();
        List<String> tiposVehiculo = tarifaRepository.findDistinctTipoVehiculo();
        List<String> parqueaderos = lugarParqueaderoRepository.findDistinctNombres();
        List<String> lavadores = lavadorRepository.findAllNombresOrderByNombre();

        return new FiltrosDTO(usuarios, tiposVehiculo, parqueaderos, lavadores);
    }
}
