package com.zynko.controllers.parqueadero;

import com.zynko.dtos.parqueadero.ticketsParqueadero.TicketEntradaRequest;
import com.zynko.dtos.parqueadero.ticketsParqueadero.TicketMensualidadRequest;
import com.zynko.dtos.parqueadero.ticketsParqueadero.TicketResponse;
import com.zynko.dtos.parqueadero.ticketsParqueadero.TicketSalidaRequest;
import com.zynko.mappers.parqueadero.TicketParqueaderoMapper;
import com.zynko.models.parqueadero.TicketParqueadero;
import com.zynko.services.parqueadero.ticket.TicketParqueaderoService;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/api/tickets")
public class TicketParqueaderoController {

    @Autowired
    private TicketParqueaderoService ticketParqueaderoService;

    @Autowired
    private TicketParqueaderoMapper ticketParqueaderoMapper;

    // Buscar todos los tickets
    @GetMapping
    public ResponseEntity<?> obtenerTodosLosTickets(
            @PageableDefault(size = 10, sort = "fechaHoraEntrada", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String codigo,
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String tipoVehiculo,
            @RequestParam(required = false) String usuarioRecibio,
            @RequestParam(required = false) String usuarioEntrego,
            @RequestParam(required = false) String parqueadero,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @RequestParam(required = false) Boolean pagado) {
        try {
            Page<TicketParqueadero> tickets = ticketParqueaderoService.buscarTodos(
                    pageable, codigo, placa, tipoVehiculo, usuarioRecibio, usuarioEntrego, fechaInicio, fechaFin, pagado, parqueadero);

            if (tickets.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay tickets disponibles");
            }

            Page<TicketResponse> response = tickets.map(ticketParqueaderoMapper::toResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener tickets: " + e.getMessage());
        }
    }

    // Buscar ticket por ID
    @GetMapping("/id/{id}")
    public ResponseEntity<?> obtenerTicketPorId(@PathVariable Long id) {
        try {
            Optional<TicketParqueadero> ticketOpt = ticketParqueaderoService.buscarPorId(id);
            if (ticketOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket con ID " + id + " no encontrado");
            }
            return ResponseEntity.ok(ticketParqueaderoMapper.toResponse(ticketOpt.get()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener ticket: " + e.getMessage());
        }
    }

    // Buscar ticket por código
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<?> obtenerTicketPorCodigo(@PathVariable String codigo) {
        try {
            TicketParqueadero ticketParqueadero = ticketParqueaderoService.buscarTicketCodigo(codigo);
            if (ticketParqueadero == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket con código " + codigo + " no encontrado");
            }
            return ResponseEntity.ok(ticketParqueaderoMapper.toResponse(ticketParqueadero));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener ticket: " + e.getMessage());
        }
    }

    // Crear ticket entrada vehículo
    @PostMapping("/entrada")
    public ResponseEntity<?> crearTicket(@RequestBody TicketEntradaRequest ticketRequest) {
        try {
            TicketResponse response = ticketParqueaderoMapper.toResponse(ticketParqueaderoService.ticketEntradaVehiculo(ticketRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear ticket: " + e.getMessage());
        }
    }

    // Crear ticket mensualidad
    @PostMapping("/mensualidad")
    public ResponseEntity<?> mensualidadTicket(@RequestBody TicketMensualidadRequest ticketRequest) {
        try {
            TicketResponse response = ticketParqueaderoMapper.toResponse(ticketParqueaderoService.ticketMensualidad(ticketRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear ticket: " + e.getMessage());
        }
    }

    // Actualizar salida ticket
    @PutMapping("/salida")
    public ResponseEntity<?> salidaTicket(@RequestBody TicketSalidaRequest ticketRequest) {
        try {
            TicketResponse response = ticketParqueaderoMapper.toResponse(ticketParqueaderoService.actualizarTicketSalida(ticketRequest));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar ticket: " + e.getMessage());
        }
    }

    // Eliminar ticket
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTicket(@PathVariable Long id) {
        try {
            Optional<TicketParqueadero> ticketOpt = ticketParqueaderoService.buscarPorId(id);
            if (ticketOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket con ID " + id + " no encontrado");
            }
            ticketParqueaderoService.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar ticket: " + e.getMessage());
        }
    }
}
