package com.zynko.controllers.isla;

import com.zynko.dtos.isla.gasto.GastoRequest;
import com.zynko.models.isla.Gasto;
import com.zynko.services.isla.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @GetMapping("/turno-activo")
    public ResponseEntity<?> obtenerGastosDelTurnoActivo() {
        try {
            List<Gasto> gastos = gastoService.buscarGastosPorTurno();
            if (gastos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encontraron gastos para el turno activo.");
            }
            return ResponseEntity.ok(gastos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener los gastos: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearGasto(@RequestBody GastoRequest gastoRequest) {
        try {
            Gasto nuevoGasto = gastoService.crearGasto(gastoRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoGasto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gasto> actualizarGasto(@PathVariable Long id, @RequestBody GastoRequest gastoDetalles) {
        try {
            return gastoService.actualizar(id, gastoDetalles)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Gasto> anularGasto(@PathVariable Long id) {
        try {
            Gasto tiroAnulado = gastoService.anularGasto(id);
            return ResponseEntity.ok(tiroAnulado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}