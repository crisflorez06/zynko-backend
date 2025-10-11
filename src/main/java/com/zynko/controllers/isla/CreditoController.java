package com.zynko.controllers.isla;

import com.zynko.dtos.isla.creditos.CreditoRequest;
import com.zynko.dtos.isla.creditos.CreditoFormularioDTO;
import com.zynko.dtos.isla.creditos.CreditoResponseDTO;
import com.zynko.mappers.isla.CreditoMapper;
import com.zynko.models.isla.credito.Credito;
import com.zynko.services.isla.credito.CreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/creditos")
public class CreditoController {

    @Autowired
    private CreditoService creditoService;

    @Autowired
    private CreditoMapper creditoMapper;

    @PostMapping
    public ResponseEntity<?> crearCredito(@RequestBody CreditoRequest creditoRequest) {
        try {
            CreditoResponseDTO response = creditoService.crearCredito(creditoRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el crédito: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CreditoResponseDTO>> obtenerTodosLosCreditos() {
        List<Credito> creditos = creditoService.buscarTodos();
        List<CreditoResponseDTO> response = creditos.stream()
                .map(creditoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCreditoPorId(@PathVariable Long id) {
        try {
            Credito credito = creditoService.buscarPorId(id);
            return ResponseEntity.ok(creditoMapper.toResponse(credito));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/turno-activo")
    public ResponseEntity<List<CreditoResponseDTO>> obtenerCreditosDelTurnoActivo() {
        List<Credito> creditos = creditoService.buscarCreditosPorTurno();
        List<CreditoResponseDTO> response = creditos.stream()
                .map(creditoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCredito(@PathVariable Long id, @RequestBody CreditoRequest creditoRequest) {
        try {
            CreditoResponseDTO response = creditoService.actualizar(id, creditoRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listar-productos-clientes")
    public ResponseEntity<?> listarProductosClientes() {
        try {
            CreditoFormularioDTO response = creditoService.getDatosParaFormulario();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los datos del formulario: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCredito(@PathVariable Long id) {
        try {
            CreditoResponseDTO response = creditoService.eliminar(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el crédito: " + e.getMessage());
        }
    }

}
