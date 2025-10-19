package com.zynko.services.parqueadero.ticket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.zynko.dtos.parqueadero.cierreParqueadero.DetalleParqueaderoCierre;
import com.zynko.dtos.parqueadero.cierreParqueadero.TicketCierreParqueaderoResponse;
import com.zynko.dtos.vehiculos.TotalVehiculosDTO;
import com.zynko.dtos.vehiculos.VehiculoDTO;
import com.zynko.models.*;
import com.zynko.models.parqueadero.LugarParqueadero;
import com.zynko.models.parqueadero.PagoParqueadero;
import com.zynko.models.parqueadero.TicketParqueadero;
import com.zynko.services.UsuarioService;
import com.zynko.services.VehiculoService;
import com.zynko.services.parqueadero.LugarParqueaderoService;
import com.zynko.services.parqueadero.PagoParqueaderoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zynko.dtos.parqueadero.ticketsParqueadero.TicketEntradaRequest;
import com.zynko.dtos.parqueadero.ticketsParqueadero.TicketMensualidadRequest;
import com.zynko.dtos.parqueadero.ticketsParqueadero.TicketSalidaRequest;
import com.zynko.repositories.parqueadero.TicketParqueaderoRepository;

@Service
public class TicketParqueaderoService {

    @Autowired
    private TicketParqueaderoRepository ticketRepository;

    @Autowired
    private PagoParqueaderoService pagoParqueaderoService;

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LugarParqueaderoService lugarParqueaderoService;

    public Page<TicketParqueadero> buscarTodos(Pageable pageable, String codigo, String placa, String tipo, String usuarioRecibio,
                                               String usuarioEntrego, LocalDateTime fechaInicio, LocalDateTime fechaFin, Boolean pagado, String parqueadero) {

        Specification<TicketParqueadero> spec = TicketParqueaderoSpecification.hasCodigo(codigo)
                .and(TicketParqueaderoSpecification.hasPlaca(placa))
                .and(TicketParqueaderoSpecification.hasTipo(tipo))
                .and(TicketParqueaderoSpecification.hasUsuarioRecibio(usuarioRecibio))
                .and(TicketParqueaderoSpecification.hasUsuarioEntrego(usuarioEntrego))
                .and(TicketParqueaderoSpecification.hasParqueadero(parqueadero))
                .and(TicketParqueaderoSpecification.isPagado(pagado))
                .and(TicketParqueaderoSpecification.fechaEntradaBetween(fechaInicio, fechaFin));

        return ticketRepository.findAll(spec, pageable);
    }

    public Optional<TicketParqueadero> buscarPorId(Long id) {
        return ticketRepository.findById(id);
    }

    public void eliminarPorId(Long id) {
        ticketRepository.deleteById(id);
    }

    // como este metodo se usa para cuando hay salida de vehiculo
    // tenemos que validar que no exista un pago
    public TicketParqueadero buscarTicketCodigo(String codigoBarras) {
        if (codigoBarras != null && !codigoBarras.isEmpty()) {
            TicketParqueadero ticketParqueadero = ticketRepository.findByCodigo(codigoBarras);

            if (ticketParqueadero != null) {
                //este if es utilizado para que si el estado ya es verdadero no me devuelva el ticket
                //ya que sobrescribiria datos
                if (!ticketParqueadero.getPago().getEstado()) {
                    return ticketParqueadero;
                }
            }
        }
        return null;
    }

    private String generarCodigo(String placa, LocalDateTime fechaEntrada) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("La placa no puede ser nula o vacía para generar el código.");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String fechaFormateada = fechaEntrada.format(formatter);
        return placa.toUpperCase() + fechaFormateada;
    }

    // logica para crear el ticket cuando se entra un vehiculo
    @Transactional
    public TicketParqueadero ticketEntradaVehiculo(TicketEntradaRequest ticketRequest) {

        Usuario usuarioRecibio = usuarioService.buscarPorId(ticketRequest.getUsuarioRecibioId());

        Vehiculo vehiculo = vehiculoService.crearVehiculo(ticketRequest.getPlaca(), ticketRequest.getTipoVehiculo());

        LugarParqueadero lugarParqueadero = lugarParqueaderoService.buscarOCrear(ticketRequest.getParqueadero());

        TicketParqueadero ticketParqueadero = new TicketParqueadero();

        ticketParqueadero.setCodigo(generarCodigo(vehiculo.getPlaca(), LocalDateTime.now()));
        ticketParqueadero.setFechaHoraEntrada(LocalDateTime.now());
        ticketParqueadero.setUsuarioRecibio(usuarioRecibio);
        ticketParqueadero.setVehiculo(vehiculo);
        ticketParqueadero.setParqueadero(lugarParqueadero);

        PagoParqueadero pagoParqueadero = new PagoParqueadero(false, 0, null, ticketParqueadero);

        ticketParqueadero.setPago(pagoParqueadero);

        return ticketRepository.save(ticketParqueadero);
    }

    // logica cuando un vehiculo va a salir
    @Transactional
    public TicketParqueadero actualizarTicketSalida(TicketSalidaRequest ticketRequest) {
        TicketParqueadero ticketParqueaderoExistente = buscarTicketCodigo(ticketRequest.getCodigo());

        if (ticketParqueaderoExistente == null) {
            throw new RuntimeException("El ticket con codigo: " + ticketRequest.getCodigo() + " no encontrado");
        }

        LocalDateTime salida = LocalDateTime.now();

        ticketParqueaderoExistente.setFechaHoraSalida(salida);
        ticketParqueaderoExistente.setUsuarioEntrego(usuarioService.buscarPorId(ticketRequest.getIdUsuarioLogueado()));
        ticketParqueaderoExistente.getPago().setEstado(true);
        ticketParqueaderoExistente.getPago().setFechaHora(salida);
        ticketParqueaderoExistente.getPago().setTotal(pagoParqueaderoService.calcularTotal(ticketRequest.getCodigo()));

        return ticketRepository.save(ticketParqueaderoExistente);
    }

    // logica para manejar mensualidad
    @Transactional
    public TicketParqueadero ticketMensualidad(TicketMensualidadRequest ticketRequest) {

        Usuario usuario = usuarioService.buscarPorId(ticketRequest.getUsuarioId());

        Vehiculo vehiculo = vehiculoService.crearVehiculo(ticketRequest.getPlaca(), ticketRequest.getTipoVehiculo());

        LugarParqueadero lugarParqueadero = lugarParqueaderoService.buscarOCrear(ticketRequest.getParqueadero());

        TicketParqueadero ticketParqueadero = new TicketParqueadero();

        ticketParqueadero.setCodigo("MENSUALIDAD" + generarCodigo(vehiculo.getPlaca(), LocalDateTime.now()));
        ticketParqueadero.setFechaHoraEntrada(ticketRequest.getFechaHoraEntrada());
        ticketParqueadero.setFechaHoraSalida(ticketRequest.getFechaHoraEntrada().plusDays(ticketRequest.getDias()));
        ticketParqueadero.setUsuarioRecibio(usuario);
        ticketParqueadero.setUsuarioEntrego(usuario);
        ticketParqueadero.setVehiculo(vehiculo);
        ticketParqueadero.setParqueadero(lugarParqueadero);

        PagoParqueadero pagoParqueadero = new PagoParqueadero(true, ticketRequest.getTotal(), LocalDateTime.now(), ticketParqueadero);

        ticketParqueadero.setPago(pagoParqueadero);

        return ticketRepository.save(ticketParqueadero);
    }

    private List<TotalVehiculosDTO> calcularTotalesPorTipo(List<TicketParqueadero> ticketParqueaderos) {
        if (ticketParqueaderos == null || ticketParqueaderos.isEmpty()) {
            return new ArrayList<>();
        }

        return ticketParqueaderos.stream()
                .map(TicketParqueadero::getVehiculo)
                .collect(Collectors.groupingBy(Vehiculo::getTipo, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new TotalVehiculosDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    //aca se genera los datos para pasarselo despues al servicio de cierre turno
    public TicketCierreParqueaderoResponse generarDatosCierre(LocalDateTime fechaInicio, LocalDateTime fechaFinal) {

        List<LugarParqueadero> todosLosLugarParqueaderos = lugarParqueaderoService.buscarTodos();
        List<TicketParqueadero> ticketsCierre = ticketRepository.findTicketsParaCierre(fechaInicio, fechaFinal);
        List<TicketParqueadero> ticketsParqueadero = ticketRepository.findTicketsAbiertos();


        TicketCierreParqueaderoResponse cierreTurno = new TicketCierreParqueaderoResponse();
        cierreTurno.setFechaInicio(fechaInicio);
        cierreTurno.setFechaCierre(fechaFinal);

        Map<String, DetalleParqueaderoCierre> mapaDetalles = new HashMap<>();

        for (LugarParqueadero lugarParqueadero : todosLosLugarParqueaderos) {

            if (lugarParqueadero == null) {
                System.out.println("ADVERTENCIA: Se encontró un objeto Parqueadero nulo en la lista. Saltando esta iteración.");
                continue;
            }
            if (lugarParqueadero.getNombre() == null) {
                System.out.println("ADVERTENCIA: El parqueadero con ID: " + lugarParqueadero.getId() + " tiene un nombre nulo. Saltando la creación de detalles para este parqueadero.");
                continue;
            }

            DetalleParqueaderoCierre detalle = new DetalleParqueaderoCierre();
            Long parqueaderoId = lugarParqueadero.getId();

            // --- A. Filtrar listas para este parqueadero ---
            // Vehículos que entraron en el turno
            List<TicketParqueadero> entrantes = ticketsCierre.stream()
                    .filter(t -> t.getParqueadero().getId().equals(parqueaderoId) &&
                            t.getFechaHoraEntrada().isAfter(fechaInicio.minusNanos(1)) &&
                            t.getFechaHoraEntrada().isBefore(fechaFinal.plusNanos(1)))
                    .collect(Collectors.toList());

            // Vehículos que salieron en el turno
            List<TicketParqueadero> salientes = ticketsCierre.stream()
                    .filter(t -> t.getParqueadero().getId().equals(parqueaderoId) &&
                            t.getFechaHoraSalida() != null &&
                            t.getFechaHoraSalida().isAfter(fechaInicio.minusNanos(1)) &&
                            t.getFechaHoraSalida().isBefore(fechaFinal.plusNanos(1)))
                    .collect(Collectors.toList());

            // Vehículos que siguen adentro (usando la segunda lista)
            List<TicketParqueadero> dentroParqueadero = ticketsParqueadero.stream()
                    .filter(t -> t.getParqueadero().getId().equals(parqueaderoId))
                    .collect(Collectors.toList());

            // Vehículos en mensualidad
            List<TicketParqueadero> mensualidad = ticketsCierre.stream()
                    .filter(t -> t.getParqueadero().getId().equals(parqueaderoId) &&
                            t.getCodigo().contains("MENSUALIDAD") &&
                            t.getPago().getFechaHora().isAfter(fechaInicio.minusNanos(1)) &&
                            t.getPago().getFechaHora().isBefore(fechaFinal.plusNanos(1)))
                    .collect(Collectors.toList());

            // --- B. Asignar listas de vehículos al DTO de detalle ---
            detalle.setListaVehiculosEntrantes(
                    entrantes.stream().map(TicketParqueadero::getVehiculo).collect(Collectors.toList())
            );
            detalle.setListaVehiculosSalientes(
                    salientes.stream().map(ticket -> new VehiculoDTO(
                            ticket.getVehiculo().getPlaca(),
                            ticket.getVehiculo().getTipo(),
                            ticket.getPago().getTotal()
                    )).collect(Collectors.toList())
            );
            detalle.setVehiculosEnParqueadero(
                    dentroParqueadero.stream().map(TicketParqueadero::getVehiculo).collect(Collectors.toList())
            );
            detalle.setVehiculosMensualidad(
                    mensualidad.stream().map(ticket -> new VehiculoDTO(
                            ticket.getVehiculo().getPlaca(),
                            ticket.getVehiculo().getTipo(),
                            ticket.getPago().getTotal()
                    )).collect(Collectors.toList())
            );

            // --- C. Calcular el total pagado en el turno para este parqueadero ---
            int totalPagado = ticketsCierre.stream()
                    .filter(t -> t.getParqueadero().getId().equals(parqueaderoId) &&
                            t.getPago() != null && t.getPago().getFechaHora() != null &&
                            t.getPago().getFechaHora().isAfter(fechaInicio.minusNanos(1)) &&
                            t.getPago().getFechaHora().isBefore(fechaFinal.plusNanos(1)))
                    .mapToInt(t -> t.getPago().getTotal())
                    .sum();
            detalle.setTotalAPagar(totalPagado);

            // --- D. Calcular los totales por tipo de vehículo ---
            detalle.setListaTiposVehiculosEntrantes(calcularTotalesPorTipo(entrantes));

            detalle.setListaTiposVehiculosSalientes(calcularTotalesPorTipo(salientes));

            detalle.setListaTiposVehiculosParqueadero(calcularTotalesPorTipo(dentroParqueadero));

            // --- E. Añadir el detalle completado al mapa principal ---
            System.out.println("Procesando parqueadero: " + lugarParqueadero.getNombre());
            mapaDetalles.put(lugarParqueadero.getNombre(), detalle);
        }

        cierreTurno.setDetallesPorParqueadero(mapaDetalles);

        int totalGeneral = mapaDetalles.values().stream()
                .mapToInt(detalle -> detalle.getTotalAPagar() != null ? detalle.getTotalAPagar() : 0)
                .sum();
        cierreTurno.setTotal(totalGeneral);

        return cierreTurno;

    }

}
