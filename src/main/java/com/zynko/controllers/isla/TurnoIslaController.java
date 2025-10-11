package com.zynko.controllers.isla;

import com.zynko.dtos.isla.turnoIsla.Numeracion;
import com.zynko.dtos.isla.turnoIsla.TurnoIslaResponse;
import com.zynko.models.isla.TurnoIsla;
import com.zynko.services.isla.TurnoIslaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/turno-activo")
    public ResponseEntity<?> getTurnoActivo() {
        try {
            TurnoIslaResponse turnoActivo = turnoIslaService.getTurnoActivoResponse();
            return ResponseEntity.ok(turnoActivo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/editar-inicial")
    public ResponseEntity<?> actualizarNumeracionInicial(@RequestBody Numeracion numeracionDto) {
        try {
            Numeracion numeracionActualizada = turnoIslaService.editarNumeracionInicial(numeracionDto);
            return ResponseEntity.ok(numeracionActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar la numeración: " + e.getMessage());
        }
    }

    @PostMapping("/calcular-total")
    public ResponseEntity<?> calcularVentas(@RequestBody Numeracion numeracionFinal) {
        try {
            Integer totalVentas = turnoIslaService.calcularVentasCombustible(numeracionFinal);
            return ResponseEntity.ok(totalVentas);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al calcular las ventas: " + e.getMessage());
        }
    }

    @PatchMapping("/editar-visas")
    public ResponseEntity<?> editarVisas(@RequestBody Map<String, Integer> body) {
        try {
            Integer visas = body.get("totalVisas");
            if (visas == null || visas < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El campo 'totalVisas' es requerido.");
            }
            return ResponseEntity.ok(turnoIslaService.editarVisa(visas));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el total de las visas: " + e.getMessage());
        }
    }

    @PostMapping("/cuadre")
    public ResponseEntity<?> calcularCuadre() {
        try {
            Integer cuadre = turnoIslaService.calcularCuadre();
            return ResponseEntity.ok(cuadre);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al calcular el cuadre: " + e.getMessage());
        }
    }
}
