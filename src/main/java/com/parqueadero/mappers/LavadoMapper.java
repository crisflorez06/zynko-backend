package com.parqueadero.mappers;

import com.parqueadero.dtos.lavadero.LavadoRequest;
import com.parqueadero.dtos.lavadero.LavadoResponse;
import com.parqueadero.models.Lavado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LavadoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "turnoIsla", ignore = true)
    @Mapping(target = "lavadorEntity", ignore = true)
    Lavado toEntity(LavadoRequest request);

    @Mapping(target = "lavadorId", expression = "java(lavado.getLavadorEntity() != null ? lavado.getLavadorEntity().getId() : null)")
    LavadoResponse toResponse(Lavado lavado);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "turnoIsla", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "lavadorEntity", ignore = true)
    void actualizarDesdeRequest(LavadoRequest request, @MappingTarget Lavado lavado);
}
