package com.parqueadero.services;

import com.parqueadero.models.TurnoIsla;
import com.parqueadero.repositories.TurnoIslaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoIslaService {

    @Autowired
    private TurnoIslaRepository turnoIslaRepository;

    public List<TurnoIsla> buscarTodos() {
        return turnoIslaRepository.findAll();
    }

    public TurnoIsla buscarPorId(Long id) {
        return turnoIslaRepository.findById(id).orElseThrow(() -> new RuntimeException("TurnoIsla no encontrado con id: " + id));
    }

    public TurnoIsla guardar(TurnoIsla turnoIsla) {
        return turnoIslaRepository.save(turnoIsla);
    }

    public void eliminarPorId(Long id) {
        turnoIslaRepository.deleteById(id);
    }

    public Optional<TurnoIsla> actualizar(Long id, TurnoIsla detalles) {
        return turnoIslaRepository.findById(id).map(turno -> {
            // Aquí se mapearían los campos que se pueden actualizar
            // Por ejemplo: turno.setFechaFinal(detalles.getFechaFinal());
            return turnoIslaRepository.save(turno);
        });
    }
}
