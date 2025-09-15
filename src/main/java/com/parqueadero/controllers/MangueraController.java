package com.parqueadero.controllers;

import com.parqueadero.models.Manguera;
import com.parqueadero.services.MangueraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mangueras")
public class MangueraController {

    @Autowired
    private MangueraService mangueraService;

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        try {
            List<Manguera> mangueras = mangueraService.buscarTodos();
            if (mangueras.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay mangueras registradas");
            }
            return ResponseEntity.ok(mangueras);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener mangueras: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Manguera manguera = mangueraService.buscarPorId(id);
            return ResponseEntity.ok(manguera);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener manguera: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Manguera manguera) {
        try {
            Manguera nuevaManguera = mangueraService.guardar(manguera);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaManguera);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear manguera: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Manguera detalles) {
        try {
            return mangueraService.actualizar(id, detalles)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Manguera con ID " + id + " no encontrada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar manguera: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            mangueraService.buscarPorId(id);
            mangueraService.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar manguera: " + e.getMessage());
        }
    }
}
