package com.parqueadero.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CombustibleService {

    @Autowired
    private CombustibleRepository combustibleRepository;

    public List<Combustible> buscarTodos() {
        return combustibleRepository.findAll();
    }

    public Combustible buscarPorId(Long id) {
        return combustibleRepository.findById(id).orElseThrow(() -> new RuntimeException("Combustible no encontrado con id: " + id));
    }

    public Combustible guardar(Combustible combustible) {
        return combustibleRepository.save(combustible);
    }

    public void eliminarPorId(Long id) {
        combustibleRepository.deleteById(id);
    }

    public Optional<Combustible> actualizar(Long id, Combustible detalles) {
        return combustibleRepository.findById(id).map(combustible -> {
            combustible.setMangueras(detalles.getMangueras());
            combustible.setPrecioVenta(detalles.getPrecioVenta());
            combustible.setTotalDinero(detalles.getTotalDinero());
            return combustibleRepository.save(combustible);
        });
    }
}
