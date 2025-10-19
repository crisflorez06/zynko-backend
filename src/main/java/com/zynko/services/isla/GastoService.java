package com.zynko.services.isla;

import com.zynko.dtos.isla.gasto.GastoRequest;
import com.zynko.models.isla.Gasto;
import com.zynko.models.isla.TurnoIsla;
import com.zynko.repositories.isla.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private TurnoIslaService turnoIslaService;


    public Gasto buscarPorId(Long id) {
        return gastoRepository.findById(id).orElseThrow(() -> new RuntimeException("Gasto no encontrado con id: " + id));
    }

    public Optional<Gasto> actualizar(Long id, GastoRequest detalles) {
        return gastoRepository.findById(id).map(gasto -> {
            gasto.setNombre(detalles.getNombre());
            gasto.setCantidad(detalles.getCantidad());
            return gastoRepository.save(gasto);
        });
    }

    public List<Gasto> buscarGastosPorTurno() {

        TurnoIsla turnoIsla = turnoIslaService.getTurnoActivo();

        return gastoRepository.findAllByTurnoIslaId(turnoIsla.getId());
    }


    @Transactional
    public Gasto crearGasto(GastoRequest gastoRequest) {

        if (gastoRequest == null || gastoRequest.getCantidad() <= 0) {
            return null;
        }
        TurnoIsla turnoIsla = turnoIslaService.getTurnoActivo();
        Gasto gasto = new Gasto();

        gasto.setNombre(gastoRequest.getNombre());
        gasto.setCantidad(gastoRequest.getCantidad());
        gasto.setFecha(LocalDateTime.now());
        gasto.setTurnoIsla(turnoIsla);

        return gastoRepository.save(gasto);
    }

    @Transactional
    public Gasto anularGasto(Long id) {
        Gasto gasto = buscarPorId(id);
        gasto.setCantidad(0);
        return gastoRepository.save(gasto);
    }

}
