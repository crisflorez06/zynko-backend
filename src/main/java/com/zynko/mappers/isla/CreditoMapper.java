package com.zynko.mappers.isla;

import com.zynko.dtos.isla.creditos.CreditoRequest;
import com.zynko.dtos.isla.creditos.CreditoResponseDTO;
import com.zynko.models.isla.credito.Credito;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// @Mapper(componentModel = "spring") le dice a MapStruct que genere una implementación de esta interfaz
// y que la registre como un Bean de Spring, para que puedas inyectarla con @Autowired en tus servicios.
@Mapper(componentModel = "spring")
public interface CreditoMapper {

    // Este método principal convierte una entidad `Credito` completa a un `CreditoResponseDTO`.
    // MapStruct se encarga automáticamente de los campos con el mismo nombre (id, total, fecha, items).
    // Para los campos con nombres diferentes, usamos @Mapping.
    @Mapping(source = "cliente.nombre", target = "cliente")
    @Mapping(source = "cliente.id", target = "idCliente")
    CreditoResponseDTO toResponse(Credito credito);

    // Este método principal convierte un DTO `CreditoRequest` a una entidad `Credito`.
    // Se usa al crear un nuevo crédito.
    @Mapping(source = "clienteId", target = "cliente.id")
    Credito toEntity(CreditoRequest creditoRequest);

}
