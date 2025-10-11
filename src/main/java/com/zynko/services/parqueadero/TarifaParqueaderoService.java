package com.zynko.services.parqueadero;

import com.zynko.models.parqueadero.TarifaParqueadero;
import com.zynko.repositories.parqueadero.TarifaParqueaderoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarifaParqueaderoService {

    @Autowired
    private TarifaParqueaderoRepository tarifaRepository;

    public List<TarifaParqueadero> buscarTodas() {
        return tarifaRepository.findAll();
    }

    public Optional<TarifaParqueadero> buscarPorId(Long id) {
        return tarifaRepository.findById(id);
    }

    public TarifaParqueadero guardar(TarifaParqueadero tarifaParqueadero) {
        return tarifaRepository.save(tarifaParqueadero);
    }

    public void eliminarPorId(Long id) {
        tarifaRepository.deleteById(id);
    }

    public Integer buscarPorTipoVehiculo(String tipoVehiculo) {
        return tarifaRepository.findPrecioByTipoVehiculo(tipoVehiculo);
    }

    public Optional<TarifaParqueadero> actualizarTarifa(Long id, TarifaParqueadero tarifaParqueaderoDetails) {
        return tarifaRepository.findById(id).map(tarifaParqueadero -> {
            tarifaParqueadero.setTipoVehiculo(tarifaParqueaderoDetails.getTipoVehiculo());
            tarifaParqueadero.setPrecioDia(tarifaParqueaderoDetails.getPrecioDia());
            return tarifaRepository.save(tarifaParqueadero);
        });
    }
}
