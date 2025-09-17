package com.parqueadero.mappers;

import com.parqueadero.dtos.turnoIsla.Numeracion;
import com.parqueadero.dtos.turnoIsla.TurnoIslaResponse;
import com.parqueadero.models.TurnoIsla;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TurnoIslaMapper {

    @Autowired
    private NumeracionMapper numeracionMapper;

    /**
     * Convierte una entidad TurnoIsla a un DTO de respuesta combinado.
     * El DTO de respuesta incluye los campos de la numeración inicial y el total del turno.
     * No se realizan cálculos, solo se combinan datos.
     * @param turnoIsla La entidad del turno a convertir.
     * @return Un DTO TurnoIslaResponse con los datos combinados.
     */
    public TurnoIslaResponse toResponseDto(TurnoIsla turnoIsla) {
        if (turnoIsla == null) {
            return null;
        }

        TurnoIslaResponse response = new TurnoIslaResponse();

        // 1. Obtener el objeto Numeracion desde el JSON de la numeración inicial.
        Numeracion numeracionInicial = numeracionMapper.stringToDto(turnoIsla.getNumeracionInicial());

        // 2. Si existe la numeración inicial, copiar sus valores al DTO de respuesta.
        if (numeracionInicial != null) {
            response.setGasolina1(numeracionInicial.getGasolina1());
            response.setGasolina2(numeracionInicial.getGasolina2());
            response.setGasolina3(numeracionInicial.getGasolina3());
            response.setGasolina4(numeracionInicial.getGasolina4());

            response.setDiesel1(numeracionInicial.getDiesel1());
            response.setDiesel2(numeracionInicial.getDiesel2());
            response.setDiesel3(numeracionInicial.getDiesel3());
            response.setDiesel4(numeracionInicial.getDiesel4());
        }

        // 3. Asignar el total directamente desde el valor guardado en la entidad.
        response.setTotalVentas(turnoIsla.getTotal());

        return response;
    }
}
