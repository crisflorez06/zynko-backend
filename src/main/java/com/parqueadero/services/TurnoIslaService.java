package com.parqueadero.services;

import com.parqueadero.dtos.turnoIsla.Numeracion;
import com.parqueadero.mappers.NumeracionMapper;
import com.parqueadero.models.TurnoIsla;
import com.parqueadero.models.Usuario;
import com.parqueadero.repositories.TurnoIslaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoIslaService {

    @Autowired
    private TurnoIslaRepository turnoIslaRepository;

    @Autowired
    private NumeracionMapper numeracionMapper;

    public List<TurnoIsla> buscarTodos() {
        return turnoIslaRepository.findAll();
    }

    public TurnoIsla buscarPorId(Long id) {
        return turnoIslaRepository.findById(id).orElseThrow(() -> new RuntimeException("TurnoIsla no encontrado con id: " + id));
    }

    public void eliminarPorId(Long id) {
        turnoIslaRepository.deleteById(id);
    }

    public String ultimaNumeracion() {

        return turnoIslaRepository.findTopByOrderByIdDesc()
                .map(TurnoIsla::getNumeracionFinal)
                .orElseGet(() -> {
                    Numeracion dtoPorDefecto = new Numeracion();
                    return numeracionMapper.dtoToString(dtoPorDefecto);
                });
    }

    public TurnoIsla crearNuevoTurno(Usuario usuario){

        // Mensaje para saber que entramos al método

        TurnoIsla turnoIsla = new TurnoIsla();

        // Llamamos al método para obtener la numeración
        String numeracionInicialJson = ultimaNumeracion();

        // --- ¡PASO CLAVE DE DEPURACIÓN! ---
        // Imprimimos en la consola el valor exacto que obtuvimos del método anterior.
        // Esto nos dirá la verdad sobre lo que está pasando.

        // Asignamos el valor al objeto
        turnoIsla.setNumeracionInicial(numeracionInicialJson);

        // Asignamos el resto de los datos
        turnoIsla.setUsuario(usuario);
        turnoIsla.setFechaInicio(usuario.getFechaInicioSesion());


        return turnoIslaRepository.save(turnoIsla);
    }

    public Numeracion numeracionTurnoActivo() {

        Optional<TurnoIsla> turnoActivo = turnoIslaRepository.findTopByFechaFinalIsNullOrderByIdDesc();

        return turnoActivo
                .map(turno -> {
                    String jsonInicial = turno.getNumeracionInicial();
                    return numeracionMapper.stringToDto(jsonInicial);
                })
                .orElse(null);
    }
}
