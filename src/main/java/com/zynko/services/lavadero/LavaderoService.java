package com.zynko.services.lavadero;

import com.zynko.dtos.lavado.lavadero.LavadoRequest;
import com.zynko.dtos.lavado.lavadero.LavadoResponse;
import com.zynko.dtos.lavado.lavadero.ResumenLavadorDTO;
import com.zynko.dtos.lavado.lavadero.ResumenLavaderoResponse;
import com.zynko.dtos.vehiculos.VehiculoDTO;
import com.zynko.mappers.lavadero.LavadoMapper;
import com.zynko.models.lavadero.Lavado;
import com.zynko.models.lavadero.Lavador;
import com.zynko.models.isla.TurnoIsla;
import com.zynko.repositories.lavadero.LavadoRepository;
import com.zynko.services.isla.TurnoIslaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LavaderoService {

    @Autowired
    private LavadoRepository lavadoRepository;

    @Autowired
    private TurnoIslaService turnoIslaService;

    @Autowired
    private LavadoMapper lavadoMapper;

    @Autowired
    private LavadorService lavadorService;

    public List<LavadoResponse> listarLavadosTurnoActivo() {
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicioDia = hoy.atStartOfDay();
        LocalDateTime finDia = inicioDia.plusDays(1);
        List<Lavado> lavados = lavadoRepository.findByFechaRegistroBetweenOrderByFechaRegistroDesc(inicioDia, finDia);
        return lavados.stream()
                .map(lavadoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public LavadoResponse registrarLavado(LavadoRequest request) {
        TurnoIsla turno = turnoIslaService.getTurnoActivo();
        Lavado lavado = lavadoMapper.toEntity(request);
        lavado.setTurnoIsla(turno);
        lavado.setPagado(false);
        asignarLavador(request, lavado);
        Lavado guardado = lavadoRepository.save(lavado);
        return lavadoMapper.toResponse(guardado);
    }

    public Lavado buscarLavadoPorId(Long id) {
        return lavadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lavado no encontrado con id: " + id));
    }

    @Transactional
    public LavadoResponse actualizarLavado(Long id, LavadoRequest request) {
        Lavado lavado = buscarLavadoPorId(id);
        lavadoMapper.actualizarDesdeRequest(request, lavado);
        asignarLavador(request, lavado);
        Lavado guardado = lavadoRepository.save(lavado);
        return lavadoMapper.toResponse(guardado);
    }

    @Transactional
    public LavadoResponse marcarPago(Long id, boolean pagado) {
        Lavado lavado = buscarLavadoPorId(id);
        lavado.setPagado(pagado);
        Lavado guardado = lavadoRepository.save(lavado);
        return lavadoMapper.toResponse(guardado);
    }

    public LavadoResponse obtenerLavadoPorId(Long id) {
        return lavadoMapper.toResponse(buscarLavadoPorId(id));
    }

    public ResumenLavaderoResponse obtenerResumenTurnoActivo() {
        TurnoIsla turno = turnoIslaService.getTurnoActivo();
        List<Lavado> lavados = lavadoRepository.findAllByTurnoIslaIdOrderByFechaRegistroDesc(turno.getId());

        int totalPagado = lavados.stream()
                .filter(Lavado::isPagado)
                .mapToInt(l -> l.getValorTotal() != null ? l.getValorTotal() : 0)
                .sum();

        int totalPendiente = lavados.stream()
                .filter(lavado -> !lavado.isPagado())
                .mapToInt(l -> l.getValorTotal() != null ? l.getValorTotal() : 0)
                .sum();

        int totalEstacion = totalPagado / 2;
        int totalLavadores = totalPagado - totalEstacion;

        Map<String, List<Lavado>> agrupadosPorLavador = lavados.stream()
                .collect(Collectors.groupingBy(l -> {
                    String lavador = l.getLavador();
                    return lavador != null && !lavador.isBlank() ? lavador : "Sin asignar";
                }));

        List<ResumenLavadorDTO> detalleLavadores = agrupadosPorLavador.entrySet()
                .stream()
                .map(entry -> {
                    List<Lavado> lavadosPorLavador = entry.getValue();

                    List<VehiculoDTO> vehiculosLavador = lavadosPorLavador.stream()
                            .filter(Lavado::isPagado)
                            .map(l -> new VehiculoDTO(
                                    l.getPlaca(),
                                    l.getTipoVehiculo(),
                                    l.getValorTotal()
                            ))
                            .collect(Collectors.toList());

                    int totalLavador = vehiculosLavador.stream()
                            .mapToInt(v -> v.getTotalCobrado() != null ? v.getTotalCobrado() : 0)
                            .sum();

                    int paraLavador = totalLavador / 2;
                    int paraEstacion = totalLavador - paraLavador;

                    return new ResumenLavadorDTO(
                            entry.getKey(),
                            totalLavador,
                            paraEstacion,
                            paraLavador,
                            vehiculosLavador
                    );
                })
                .sorted(Comparator.comparing(ResumenLavadorDTO::getLavador))
                .collect(Collectors.toList());

        return new ResumenLavaderoResponse(
                lavados.size(),
                totalPagado,
                totalEstacion,
                totalLavadores,
                totalPendiente,
                detalleLavadores
        );
    }

    private void asignarLavador(LavadoRequest request, Lavado lavado) {
        Lavador lavador = null;
        if (request.getLavadorId() != null) {
            lavador = lavadorService.obtenerPorId(request.getLavadorId());
        } else if (request.getLavador() != null && !request.getLavador().isBlank()) {
            lavador = lavadorService.buscarPorNombre(request.getLavador());
        }

        if (lavador != null) {
            lavado.setLavadorEntity(lavador);
            lavado.setLavador(lavador.getNombre());
        } else {
            lavado.setLavadorEntity(null);
            if (request.getLavador() != null) {
                lavado.setLavador(request.getLavador());
            }
        }
    }
}
