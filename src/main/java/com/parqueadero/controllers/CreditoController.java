package com.parqueadero.controllers;

import com.parqueadero.models.Credito;
import com.parqueadero.services.CreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creditos")
public class CreditoController {

    @Autowired
    private CreditoService creditoService;

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        try {
            List<Credito> creditos = creditoService.buscarTodos();
            if (creditos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay creditos registrados");
            }
            return ResponseEntity.ok(creditos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener creditos: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Credito credito = creditoService.buscarPorId(id);
            return ResponseEntity.ok(credito);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener credito: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Credito credito) {
        try {
            Credito nuevoCredito = creditoService.guardar(credito);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCredito);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear credito: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Credito detalles) {
        try {
            return creditoService.actualizar(id, detalles)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Credito con ID " + id + " no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar credito: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            creditoService.buscarPorId(id);
            creditoService.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar credito: " + e.getMessage());
        }
    }
}
