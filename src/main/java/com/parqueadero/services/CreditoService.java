package com.parqueadero.services;

import com.parqueadero.models.Credito;
import com.parqueadero.repositories.CreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditoService {

    @Autowired
    private CreditoRepository creditoRepository;

    public List<Credito> buscarTodos() {
        return creditoRepository.findAll();
    }

    public Credito buscarPorId(Long id) {
        return creditoRepository.findById(id).orElseThrow(() -> new RuntimeException("Credito no encontrado con id: " + id));
    }

    public Credito guardar(Credito credito) {
        return creditoRepository.save(credito);
    }

    public void eliminarPorId(Long id) {
        creditoRepository.deleteById(id);
    }

    public Optional<Credito> actualizar(Long id, Credito detalles) {
        return creditoRepository.findById(id).map(credito -> {
            credito.setCliente(detalles.getCliente());
            credito.setProductos(detalles.getProductos());
            credito.setTotal(detalles.getTotal());
            credito.setFecha(detalles.getFecha());
            credito.setPlaca(detalles.getPlaca());
            return creditoRepository.save(credito);
        });
    }
}
