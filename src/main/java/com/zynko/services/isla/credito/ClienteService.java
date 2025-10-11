package com.zynko.services.isla.credito;

import com.zynko.models.isla.credito.Cliente;
import com.zynko.repositories.isla.credito.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
    }

    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void eliminarPorId(Long id) {
        clienteRepository.deleteById(id);
    }

    public Optional<Cliente> actualizar(Long id, Cliente detalles) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNombre(detalles.getNombre());
            cliente.setNit(detalles.getNit());
            return clienteRepository.save(cliente);
        });
    }
}
