package com.parqueadero.controllers;

import com.parqueadero.models.Tiro;
import com.parqueadero.services.TiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tiros")
public class TiroController {

    @Autowired
    private TiroService tiroService;

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        try {
            List<Tiro> tiros = tiroService.buscarTodos();
            if (tiros.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay tiros registrados");
            }
            return ResponseEntity.ok(tiros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener tiros: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Tiro tiro = tiroService.buscarPorId(id);
            return ResponseEntity.ok(tiro);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener tiro: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Tiro tiro) {
        try {
            Tiro nuevoTiro = tiroService.guardar(tiro);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTiro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear tiro: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Tiro detalles) {
        try {
            return tiroService.actualizar(id, detalles)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tiro con ID " + id + " no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar tiro: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            tiroService.buscarPorId(id);
            tiroService.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar tiro: " + e.getMessage());
        }
    }
}
