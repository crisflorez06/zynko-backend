package com.zynko.controllers.isla;

import com.zynko.models.isla.Tiro;
import com.zynko.services.isla.TiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tiros")
public class TiroController {

    @Autowired
    private TiroService tiroService;

    @GetMapping("/turno-activo")
    public ResponseEntity<?> obtenerTirosDelTurnoActivo() {
        try {
            List<Tiro> tiros = tiroService.buscarTirosPorTurno();
            if (tiros.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encontraron tiros para el turno activo.");
            }
            return ResponseEntity.ok(tiros);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener los tiros: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearTiro(@RequestBody Integer cantidad) {
        try {
            if (cantidad == null || cantidad <= 0) {
                return ResponseEntity.badRequest().build();
            }
            Tiro nuevoTiro = tiroService.crearTiro(cantidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTiro);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tiro> actualizarTiro(@PathVariable Long id, @RequestBody Tiro tiroDetalles) {
        try {
            return tiroService.actualizar(id, tiroDetalles)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Tiro> anularTiro(@PathVariable Long id) {
        try {
            Tiro tiroAnulado = tiroService.anularTiro(id);
            return ResponseEntity.ok(tiroAnulado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}