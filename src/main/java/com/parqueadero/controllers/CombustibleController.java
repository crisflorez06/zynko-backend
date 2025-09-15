package com.parqueadero.controllers;

import com.parqueadero.models.Combustible;
import com.parqueadero.services.CombustibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/combustibles")
public class CombustibleController {

    @Autowired
    private CombustibleService combustibleService;

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        try {
            List<Combustible> combustibles = combustibleService.buscarTodos();
            if (combustibles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay combustibles registrados");
            }
            return ResponseEntity.ok(combustibles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener combustibles: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Combustible combustible = combustibleService.buscarPorId(id);
            return ResponseEntity.ok(combustible);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener combustible: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Combustible combustible) {
        try {
            Combustible nuevoCombustible = combustibleService.guardar(combustible);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCombustible);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear combustible: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Combustible detalles) {
        try {
            return combustibleService.actualizar(id, detalles)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Combustible con ID " + id + " no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar combustible: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            combustibleService.buscarPorId(id);
            combustibleService.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar combustible: " + e.getMessage());
        }
    }
}
