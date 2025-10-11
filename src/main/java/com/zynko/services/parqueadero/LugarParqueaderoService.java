package com.zynko.services.parqueadero;

import com.zynko.models.parqueadero.LugarParqueadero;
import com.zynko.repositories.parqueadero.LugarParqueaderoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LugarParqueaderoService {

    @Autowired
    private LugarParqueaderoRepository lugarParqueaderoRepository;

    @Transactional
    public LugarParqueadero buscarOCrear(String nombre) {
        return lugarParqueaderoRepository.findByNombre(nombre).orElseGet(() -> {
            LugarParqueadero nuevoLugarParqueadero = new LugarParqueadero();
            nuevoLugarParqueadero.setNombre(nombre);
            return lugarParqueaderoRepository.save(nuevoLugarParqueadero);
        });
    }

    public List<LugarParqueadero> buscarTodos() {
        return lugarParqueaderoRepository.findAll();
    }
}
