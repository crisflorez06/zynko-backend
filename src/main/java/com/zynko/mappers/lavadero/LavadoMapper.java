package com.zynko.mappers.lavadero;

import com.zynko.dtos.lavado.lavadero.LavadoRequest;
import com.zynko.dtos.lavado.lavadero.LavadoResponse;
import com.zynko.models.lavadero.Lavado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LavadoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "turnoIsla", ignore = true)
    @Mapping(target = "lavadorEntity", ignore = true)
    @Mapping(target = "pagado", ignore = true)
    @Mapping(target = "tipoVehiculo", source = "tipoVehiculo")
    Lavado toEntity(LavadoRequest request);

    @Mapping(target = "lavadorId", expression = "java(lavado.getLavadorEntity() != null ? lavado.getLavadorEntity().getId() : null)")
    @Mapping(target = "lavador", expression = "java(lavado.getLavadorEntity() != null ? lavado.getLavadorEntity().getNombre() : null)")
    LavadoResponse toResponse(Lavado lavado);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "turnoIsla", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "lavadorEntity", ignore = true)
    @Mapping(target = "pagado", ignore = true)
    @Mapping(target = "tipoVehiculo", source = "tipoVehiculo")
    void actualizarDesdeRequest(LavadoRequest request, @MappingTarget Lavado lavado);
}
