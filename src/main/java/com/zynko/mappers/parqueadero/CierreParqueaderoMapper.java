package com.zynko.mappers.parqueadero;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zynko.dtos.parqueadero.cierreParqueadero.DetalleParqueaderoCierre;
import com.zynko.dtos.parqueadero.cierreParqueadero.TicketCierreParqueaderoResponse;
import com.zynko.models.isla.CierreTurno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface CierreParqueaderoMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombreUsuario", target = "nombreUsuario")
    @Mapping(source = "fechaInicioTurno", target = "fechaInicio")
    @Mapping(source = "fechaFinTurno", target = "fechaCierre")
    @Mapping(source = "totalIngresos", target = "total")
    @Mapping(target = "detallesPorParqueadero", expression = "java(parseDetalles(cierreTurno.getDetallesJson()))")
    TicketCierreParqueaderoResponse toResponse(CierreTurno cierreTurno);

    // Método auxiliar para convertir el JSON de detalles a Map
    default Map<String, DetalleParqueaderoCierre> parseDetalles(String detallesJson) {
        if (detallesJson == null || detallesJson.isEmpty()) {
            return Map.of();
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(detallesJson,
                    new TypeReference<Map<String, DetalleParqueaderoCierre>>() {
                    });
        } catch (Exception e) {
            throw new RuntimeException("Error al parsear detallesJson", e);
        }
    }
}
