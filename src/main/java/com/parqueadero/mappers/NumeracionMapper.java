package com.parqueadero.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parqueadero.dtos.turnoIsla.Numeracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Este Mapper es un componente especializado en una única tarea: la conversión
 * bidireccional entre el objeto DTO 'Numeracion' y su representación en formato String JSON.
 * Encapsula la lógica de serialización y deserialización para que pueda ser reutilizada
 * de forma segura en cualquier parte de la aplicación (por ejemplo, en TurnoIslaService).
 */
@Component
public class NumeracionMapper {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Convierte un objeto DTO de Numeracion a su representación en String JSON.
     * Este método se usa ANTES de guardar en la base de datos.
     *
     * @param numeracionDto El objeto Numeracion que se quiere convertir.
     * @return Un String que contiene el JSON del objeto.
     */
    public String dtoToString(Numeracion numeracionDto) {
        try {
            // Si el objeto que nos pasan es nulo, devolvemos nulo para no guardar "null" como texto.
            if (numeracionDto == null) {
                return null;
            }
            // objectMapper.writeValueAsString() es el método que hace la serialización (Objeto -> JSON String).
            return objectMapper.writeValueAsString(numeracionDto);
        } catch (JsonProcessingException e) {
            // Si por alguna razón el objeto no se puede convertir a JSON, lanzamos un error.
            throw new RuntimeException("Error al convertir el DTO Numeracion a String JSON.", e);
        }
    }

    /**
     * Convierte un String que contiene datos en formato JSON a un objeto DTO de Numeracion.
     * Este método se usa DESPUÉS de leer de la base de datos.
     *
     * @param jsonString El String en formato JSON que se leyó de la base de datos.
     * @return Un objeto Numeracion poblado con los datos del JSON.
     */
    public Numeracion stringToDto(String jsonString) {
        try {
            // Si el texto de la base de datos es nulo o está vacío, no hay nada que convertir.
            if (jsonString == null || jsonString.isEmpty()) {
                return null;
            }
            // objectMapper.readValue() es el método que hace la deserialización (JSON String -> Objeto).
            // Le pasamos el texto y la clase del objeto que queremos que construya.
            return objectMapper.readValue(jsonString, Numeracion.class);
        } catch (JsonProcessingException e) {
            // Si el texto no es un JSON válido, la conversión fallará. Lanzamos un error.
            throw new RuntimeException("Error al convertir el String JSON a DTO Numeracion.", e);
        }
    }
}
