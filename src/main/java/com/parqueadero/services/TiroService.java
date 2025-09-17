package com.parqueadero.services;

import com.parqueadero.models.Tiro;
import com.parqueadero.models.TurnoIsla;
import com.parqueadero.repositories.TiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TiroService {

    @Autowired
    private TiroRepository tiroRepository;

    @Autowired
    private TurnoIslaService turnoIslaService;

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

    public List<Tiro> buscarTirosPorTurno() {

        TurnoIsla turnoIsla = turnoIslaService.getTurnoActivo();

        return tiroRepository.findAllByTurnoIslaId(turnoIsla.getId());
    }

    private long getUltimoNumeroDeTiro(Long turnoIslaId) {
        // Llama al nuevo método del repositorio
        Optional<Tiro> ultimoTiroOptional = tiroRepository.findTopByTurnoIslaIdOrderByIdDesc(turnoIslaId);

        // Si se encuentra un tiro, devuelve su número. Si no, devuelve 0.
        return ultimoTiroOptional.map(Tiro::getNumero).orElse(0L);
    }

    @Transactional
    public Tiro crearTiro(Integer cantidad) {
        TurnoIsla turnoIsla = turnoIslaService.getTurnoActivo();
        Tiro tiro = new Tiro();

        long ultimoNumeroTiro = getUltimoNumeroDeTiro(turnoIsla.getId());
        tiro.setNumero(ultimoNumeroTiro + 1);
        tiro.setCantidad(cantidad);
        tiro.setFecha(LocalDateTime.now());
        tiro.setTurnoIsla(turnoIsla);

        return tiroRepository.save(tiro);
    }

    @Transactional
    public Tiro anularTiro(Long id) {
        Tiro tiro = buscarPorId(id);
        tiro.setCantidad(0);
        return tiroRepository.save(tiro);
    }

}
