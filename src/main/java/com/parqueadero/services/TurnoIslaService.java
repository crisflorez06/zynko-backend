package com.parqueadero.services;

import com.parqueadero.dtos.turnoIsla.Numeracion;
import com.parqueadero.dtos.turnoIsla.TurnoIslaResponse;
import com.parqueadero.mappers.NumeracionMapper;
import com.parqueadero.mappers.TurnoIslaMapper;
import com.parqueadero.models.TurnoIsla;
import com.parqueadero.models.Usuario;
import com.parqueadero.repositories.TurnoIslaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoIslaService {

    @Autowired
    private TurnoIslaRepository turnoIslaRepository;

    @Autowired
    private AjustesService ajustesService;

    @Autowired
    private NumeracionMapper numeracionMapper;

    @Autowired
    private TurnoIslaMapper turnoIslaMapper;

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

        TurnoIsla turnoIsla = new TurnoIsla();

        String numeracionInicialJson = ultimaNumeracion();


        turnoIsla.setNumeracionInicial(numeracionInicialJson);
        turnoIsla.setUsuario(usuario);
        turnoIsla.setFechaInicio(usuario.getFechaInicioSesion());


        return turnoIslaRepository.save(turnoIsla);
    }

    public Numeracion numeracionTurnoActivo() {

        try {
            TurnoIsla turnoActivo = getTurnoActivo();
            String jsonInicial = turnoActivo.getNumeracionInicial();
            return numeracionMapper.stringToDto(jsonInicial);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public Numeracion editarNumeracionInicial(Numeracion nuevaNumeracion) {

        TurnoIsla turnoActivo = getTurnoActivo();

        String nuevaNumeracionJson = numeracionMapper.dtoToString(nuevaNumeracion);

        turnoActivo.setNumeracionInicial(nuevaNumeracionJson);
        turnoIslaRepository.save(turnoActivo);
        return nuevaNumeracion;
    }

    //este metodo se creo para mostrar de una manera mas limpia los datos en el frontend
    public TurnoIslaResponse getTurnoActivoResponse() {
        TurnoIsla turnoActivo = getTurnoActivo();
        return turnoIslaMapper.toResponseDto(turnoActivo);
    }

    //sabemos cual es el turno activo ya que sera el que no tenga fecha de fin de turno
    public TurnoIsla getTurnoActivo() {
        return turnoIslaRepository.findTopByFechaFinalIsNullOrderByIdDesc()
                .orElseThrow(() -> new RuntimeException("No se encontró ningún turno activo en el sistema."));
    }

    public Integer calcularVentasCombustible(Numeracion numeracionFinal) {

        Numeracion numeracionInicial = numeracionTurnoActivo();
        TurnoIsla turnoIsla = getTurnoActivo();

        turnoIsla.setTotalGalonesGasolina(numeracionFinal.getTotalGasolina() - numeracionInicial.getTotalGasolina());
        turnoIsla.setTotalGalonesDiesel(numeracionFinal.getTotalDiesel() - numeracionInicial.getTotalDiesel());

        turnoIsla.setTotalVentaGasolina(turnoIsla.getTotalGalonesGasolina() * ajustesService.getPrecioGasolina());
        turnoIsla.setTotalVentaDiesel(turnoIsla.getTotalGalonesDiesel() * ajustesService.getPrecioDiesel());

        int total = (int) Math.round(turnoIsla.getTotalVentaGasolina() + turnoIsla.getTotalVentaDiesel());

        turnoIsla.setTotal(total);

        turnoIslaRepository.save(turnoIsla);

        return total;
    }

}
