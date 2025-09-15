package com.parqueadero.controllers;

import com.parqueadero.models.Cliente;
import com.parqueadero.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        try {
            List<Cliente> clientes = clienteService.buscarTodos();
            if (clientes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay clientes registrados");
            }
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener clientes: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.buscarPorId(id);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener cliente: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Cliente cliente) {
        try {
            Cliente nuevoCliente = clienteService.guardar(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear cliente: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Cliente detalles) {
        try {
            return clienteService.actualizar(id, detalles)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente con ID " + id + " no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar cliente: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            clienteService.buscarPorId(id);
            clienteService.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar cliente: " + e.getMessage());
        }
    }
}
