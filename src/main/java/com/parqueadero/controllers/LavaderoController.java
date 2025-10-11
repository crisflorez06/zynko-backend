package com.parqueadero.controllers;

import com.parqueadero.dtos.lavadero.LavadoRequest;
import com.parqueadero.dtos.lavadero.LavadoResponse;
import com.parqueadero.dtos.lavadero.ResumenLavaderoResponse;
import com.parqueadero.services.LavaderoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lavados")
public class LavaderoController {

    @Autowired
    private LavaderoService lavaderoService;

    @GetMapping("/turno-activo")
    public ResponseEntity<List<LavadoResponse>> listarLavadosTurnoActivo() {
        return ResponseEntity.ok(lavaderoService.listarLavadosTurnoActivo());
    }

    @PostMapping
    public ResponseEntity<?> registrarLavado(@RequestBody LavadoRequest request) {
        try {
            LavadoResponse respuesta = lavaderoService.registrarLavado(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No fue posible registrar el lavado: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerLavadoPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(lavaderoService.obtenerLavadoPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Lavado no encontrado con id: " + id);
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstadoPago(
            @PathVariable Long id,
            @RequestParam(name = "pagado") boolean pagado) {
        try {
            LavadoResponse respuesta = lavaderoService.marcarPago(id, pagado);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No fue posible actualizar el estado del lavado: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarLavado(@PathVariable Long id, @RequestBody LavadoRequest request) {
        try {
            LavadoResponse respuesta = lavaderoService.actualizarLavado(id, request);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Lavado no encontrado con id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No fue posible actualizar el lavado: " + e.getMessage());
        }
    }

    @GetMapping("/turno-activo/resumen")
    public ResponseEntity<ResumenLavaderoResponse> obtenerResumenTurnoActivo() {
        return ResponseEntity.ok(lavaderoService.obtenerResumenTurnoActivo());
    }
}
