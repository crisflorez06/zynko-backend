package com.parqueadero.controllers;

import com.parqueadero.dtos.lavador.LavadorRequest;
import com.parqueadero.dtos.lavador.LavadorResponse;
import com.parqueadero.models.Lavador;
import com.parqueadero.services.LavadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lavadores")
public class LavadorController {

    @Autowired
    private LavadorService lavadorService;

    @GetMapping
    public ResponseEntity<List<LavadorResponse>> listarLavadores() {
        List<LavadorResponse> lavadores = lavadorService.listarLavadores()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lavadores);
    }

    @PostMapping
    public ResponseEntity<?> crearLavador(@RequestBody LavadorRequest request) {
        try {
            Lavador lavador = lavadorService.crearLavador(request.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(lavador));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarLavador(@PathVariable Long id, @RequestBody LavadorRequest request) {
        try {
            Lavador lavador = lavadorService.actualizarLavador(id, request.getNombre());
            return ResponseEntity.ok(toResponse(lavador));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarLavador(@PathVariable Long id) {
        try {
            lavadorService.eliminarLavador(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private LavadorResponse toResponse(Lavador lavador) {
        return new LavadorResponse(lavador.getId(), lavador.getNombre());
    }
}
