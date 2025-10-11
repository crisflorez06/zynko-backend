package com.zynko.controllers.parqueadero;

import com.zynko.models.parqueadero.TarifaParqueadero;
import com.zynko.services.parqueadero.TarifaParqueaderoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaParqueaderoController {

    @Autowired
    private TarifaParqueaderoService tarifaParqueaderoService;

    @GetMapping
    public ResponseEntity<?> getAllTarifas() {
        try {
            List<TarifaParqueadero> tarifaParqueaderos = tarifaParqueaderoService.buscarTodas();
            if (tarifaParqueaderos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay tarifas disponibles");
            }
            return ResponseEntity.ok(tarifaParqueaderos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener tarifas: " + e.getMessage());
        }
    }
}
