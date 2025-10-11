package com.zynko.services.parqueadero;

import com.zynko.models.parqueadero.PagoParqueadero;
import com.zynko.models.parqueadero.TicketParqueadero;
import com.zynko.repositories.parqueadero.PagoParqueaderoRepository;
import com.zynko.repositories.parqueadero.TicketParqueaderoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PagoParqueaderoService {

    @Autowired
    private PagoParqueaderoRepository pagoParqueaderoRepository;

    @Autowired
    private TarifaParqueaderoService tarifaParqueaderoService;

    @Autowired
    private TicketParqueaderoRepository ticketParqueaderoRepository;

    public List<PagoParqueadero> buscarTodos() {
        return pagoParqueaderoRepository.findAll();
    }

    public Optional<PagoParqueadero> buscarPorId(Long id) {
        return pagoParqueaderoRepository.findById(id);
    }

    public PagoParqueadero guardar(PagoParqueadero pagoParqueadero) {
        return pagoParqueaderoRepository.save(pagoParqueadero);
    }

    public void eliminarPorId(Long id) {
        pagoParqueaderoRepository.deleteById(id);
    }


    public Integer calcularTotal(String codigo) {

        TicketParqueadero ticketParqueadero = ticketParqueaderoRepository.findByCodigo(codigo);
        if (ticketParqueadero == null) {
            throw new IllegalArgumentException("Ticket no encotrado");
        }

        Integer tarifa = tarifaParqueaderoService.buscarPorTipoVehiculo(ticketParqueadero.getVehiculo().getTipo());
        if (tarifa == null) {
            throw new IllegalArgumentException("Tarifa no encontrada para el tipo de vehículo: " + ticketParqueadero.getVehiculo().getTipo());
        }


        long minutos = Duration.between(ticketParqueadero.getFechaHoraEntrada(), LocalDateTime.now()).toMinutes();
        long horas = (long) Math.ceil(minutos / 60.0);
        int bloques = (int) Math.ceil(horas / 24.0);

        Integer total = (bloques == 0) ? tarifa : bloques * tarifa;

        return total;
    }
}
