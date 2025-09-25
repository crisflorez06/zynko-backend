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
        Numeracion numeracionFinal = numeracionMapper.stringToDto(turnoIsla.getNumeracionFinal());

        // 2. Si existe la numeración inicial, copiar sus valores al DTO de respuesta.
        if (numeracionInicial != null) {
            response.setGasolinaInicial1(numeracionInicial.getGasolina1());
            response.setGasolinaInicial2(numeracionInicial.getGasolina2());
            response.setGasolinaInicial3(numeracionInicial.getGasolina3());
            response.setGasolinaInicial4(numeracionInicial.getGasolina4());

            response.setDieselInicial1(numeracionInicial.getDiesel1());
            response.setDieselInicial2(numeracionInicial.getDiesel2());
            response.setDieselInicial3(numeracionInicial.getDiesel3());
            response.setDieselInicial4(numeracionInicial.getDiesel4());
        }

        if (numeracionFinal != null) {
            response.setGasolinaFinal1(numeracionFinal.getGasolina1());
            response.setGasolinaFinal2(numeracionFinal.getGasolina2());
            response.setGasolinaFinal3(numeracionFinal.getGasolina3());
            response.setGasolinaFinal4(numeracionFinal.getGasolina4());

            response.setDieselFinal1(numeracionFinal.getDiesel1());
            response.setDieselFinal2(numeracionFinal.getDiesel2());
            response.setDieselFinal3(numeracionFinal.getDiesel3());
            response.setDieselFinal4(numeracionFinal.getDiesel4());
        }



        response.setTotalTiros(turnoIsla.getTiros().stream()
                .mapToInt(t -> t.getCantidad() != null ? t.getCantidad() : 0)
                .sum());

        response.setTotalVentas(turnoIsla.getTotal());

        response.setTotalCreditos(turnoIsla.getCreditos().stream()
                .mapToInt(c -> c.getTotal() != null ? c.getTotal() : 0)
                .sum());

        response.setTotalVentas(turnoIsla.getTotal());
        response.setTotalVisas(turnoIsla.getVisas());

        return response;
    }
}
