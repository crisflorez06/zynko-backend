package com.parqueadero.services;

import com.parqueadero.models.Lavador;
import com.parqueadero.repositories.LavadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LavadorService {

    @Autowired
    private LavadorRepository lavadorRepository;

    public List<Lavador> listarLavadores() {
        return lavadorRepository.findAll(Sort.by(Sort.Direction.ASC, "nombre"));
    }

    public List<String> listarNombresLavadores() {
        return lavadorRepository.findAllNombresOrderByNombre();
    }

    public Lavador obtenerPorId(Long id) {
        return lavadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lavador no encontrado con id: " + id));
    }

    public Lavador buscarPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return null;
        }
        return lavadorRepository.findByNombreIgnoreCase(nombre.trim()).orElse(null);
    }

    @Transactional
    public Lavador guardar(Lavador lavador) {
        return lavadorRepository.save(lavador);
    }

    @Transactional
    public Lavador crearLavador(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new RuntimeException("El nombre del lavador es obligatorio");
        }
        String nombreNormalizado = nombre.trim();
        lavadorRepository.findByNombreIgnoreCase(nombreNormalizado)
                .ifPresent(existing -> {
                    throw new RuntimeException("Ya existe un lavador con el nombre: " + nombreNormalizado);
                });

        Lavador lavador = new Lavador();
        lavador.setNombre(nombreNormalizado);
        return lavadorRepository.save(lavador);
    }

    @Transactional
    public Lavador actualizarLavador(Long id, String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new RuntimeException("El nombre del lavador es obligatorio");
        }
        Lavador lavador = obtenerPorId(id);

        lavadorRepository.findByNombreIgnoreCase(nombre.trim())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new RuntimeException("Ya existe un lavador con el nombre: " + nombre.trim());
                });

        lavador.setNombre(nombre.trim());
        return lavadorRepository.save(lavador);
    }

    @Transactional
    public void eliminarLavador(Long id) {
        Lavador lavador = obtenerPorId(id);
        lavadorRepository.delete(lavador);
    }
}
