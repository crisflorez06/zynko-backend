package com.parqueadero.controllers;

import com.parqueadero.dtos.turnoIsla.Numeracion;
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

    @GetMapping("/numeracion-inicial")
    public ResponseEntity<?> getNumeracionTurnoActivo() {

        Numeracion numeracionActiva = turnoIslaService.numeracionTurnoActivo();

        if (numeracionActiva != null) {
            return ResponseEntity.ok(numeracionActiva);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún turno activo en el sistema.");
        }
    }
}
