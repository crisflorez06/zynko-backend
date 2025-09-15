package com.parqueadero.services;

import com.parqueadero.models.Tiro;
import com.parqueadero.repositories.TiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TiroService {

    @Autowired
    private TiroRepository tiroRepository;

    public List<Tiro> buscarTodos() {
        return tiroRepository.findAll();
    }

    public Tiro buscarPorId(Long id) {
        return tiroRepository.findById(id).orElseThrow(() -> new RuntimeException("Tiro no encontrado con id: " + id));
    }

    public Tiro guardar(Tiro tiro) {
        return tiroRepository.save(tiro);
    }

    public void eliminarPorId(Long id) {
        tiroRepository.deleteById(id);
    }

    public Optional<Tiro> actualizar(Long id, Tiro detalles) {
        return tiroRepository.findById(id).map(tiro -> {
            tiro.setNumero(detalles.getNumero());
            tiro.setCantidad(detalles.getCantidad());
            tiro.setFecha(detalles.getFecha());
            tiro.setTipoTurno(detalles.getTipoTurno());
            return tiroRepository.save(tiro);
        });
    }
}
