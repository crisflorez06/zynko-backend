package com.zynko.mappers.parqueadero;

import com.zynko.dtos.parqueadero.ticketsParqueadero.TicketResponse;
import com.zynko.models.parqueadero.TicketParqueadero;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketParqueaderoMapper {

    // Entidad → Response
    @Mapping(source = "vehiculo.placa", target = "placa")
    @Mapping(source = "vehiculo.tipo", target = "tipoVehiculo")
    @Mapping(source = "usuarioRecibio.nombre", target = "usuarioRecibio")
    @Mapping(source = "usuarioEntrego.nombre", target = "usuarioEntrego")
    @Mapping(source = "pago.estado", target = "estadoPago")
    @Mapping(source = "pago.total", target = "totalPagar")
    @Mapping(source = "parqueadero.nombre", target = "parqueadero")
    TicketResponse toResponse(TicketParqueadero ticketParqueadero);
}
