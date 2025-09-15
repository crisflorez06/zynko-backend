package com.parqueadero.controllers;

import com.parqueadero.models.TurnoIsla;
import com.parqueadero.services.TurnoIslaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turnos-isla")
public class TurnoIslaController {

    @Autowired
    private TurnoIslaService turnoIslaService;

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        try {
            List<TurnoIsla> turnos = turnoIslaService.buscarTodos();
            if (turnos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay turnos de isla registrados");
            }
            return ResponseEntity.ok(turnos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener turnos de isla: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            TurnoIsla turno = turnoIslaService.buscarPorId(id);
            return ResponseEntity.ok(turno);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener turno de isla: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody TurnoIsla turnoIsla) {
        try {
            TurnoIsla nuevoTurno = turnoIslaService.guardar(turnoIsla);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTurno);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear turno de isla: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody TurnoIsla detalles) {
        try {
            return turnoIslaService.actualizar(id, detalles)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("TurnoIsla con ID " + id + " no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar turno de isla: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            turnoIslaService.buscarPorId(id); // Verifica si existe antes de eliminar
            turnoIslaService.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar turno de isla: " + e.getMessage());
        }
    }
}
