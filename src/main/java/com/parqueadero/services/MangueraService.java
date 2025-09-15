package com.parqueadero.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MangueraService {

    @Autowired
    private MangueraRepository mangueraRepository;

    public List<Manguera> buscarTodos() {
        return mangueraRepository.findAll();
    }

    public Manguera buscarPorId(Long id) {
        return mangueraRepository.findById(id).orElseThrow(() -> new RuntimeException("Manguera no encontrada con id: " + id));
    }

    public Manguera guardar(Manguera manguera) {
        return mangueraRepository.save(manguera);
    }

    public void eliminarPorId(Long id) {
        mangueraRepository.deleteById(id);
    }

    public Optional<Manguera> actualizar(Long id, Manguera detalles) {
        return mangueraRepository.findById(id).map(manguera -> {
            manguera.setTipo(detalles.getTipo());
            manguera.setLecturaInicial(detalles.getLecturaInicial());
            manguera.setLecturaFinal(detalles.getLecturaFinal());
            manguera.setTotalGalones(detalles.getTotalGalones());
            return mangueraRepository.save(manguera);
        });
    }
}
