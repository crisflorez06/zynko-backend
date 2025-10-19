package com.zynko.services.isla;

import com.zynko.dtos.isla.turnoIsla.Numeracion;
import com.zynko.dtos.isla.turnoIsla.TurnoIslaResponse;
import com.zynko.mappers.isla.NumeracionMapper;
import com.zynko.mappers.isla.TurnoIslaMapper;
import com.zynko.models.isla.TurnoIsla;
import com.zynko.models.Usuario;
import com.zynko.repositories.isla.TurnoIslaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        turnoIsla.setActivo(true);

        return turnoIslaRepository.save(turnoIsla);
    }

    public Numeracion numeracionInicialTurnoActivo() {

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

    //en la base de datos siempre habra solo un turno activo
    public TurnoIsla getTurnoActivo() {
        return turnoIslaRepository.findByActivoTrue()
                .orElseThrow(() -> new RuntimeException("No se encontró ningún turno activo en el sistema."));
    }

    public Integer editarVisa(Integer totalVisa) {
        TurnoIsla turnoIsla = getTurnoActivo();
        turnoIsla.setVisas(totalVisa);
        turnoIslaRepository.save(turnoIsla);

        return turnoIsla.getVisas();

    }

    public Integer calcularVentasCombustible(Numeracion numeracionFinal) {

        Numeracion numeracionInicial = numeracionInicialTurnoActivo();
        String numeracionFInalJson = numeracionMapper.dtoToString(numeracionFinal);
        TurnoIsla turnoIsla = getTurnoActivo();

        turnoIsla.setTotalGalonesGasolina(numeracionFinal.getTotalGasolina() - numeracionInicial.getTotalGasolina());
        turnoIsla.setTotalGalonesDiesel(numeracionFinal.getTotalDiesel() - numeracionInicial.getTotalDiesel());

        turnoIsla.setTotalVentaGasolina(turnoIsla.getTotalGalonesGasolina() * ajustesService.getPrecioGasolina());
        turnoIsla.setTotalVentaDiesel(turnoIsla.getTotalGalonesDiesel() * ajustesService.getPrecioDiesel());

        int total = (int) Math.round(turnoIsla.getTotalVentaGasolina() + turnoIsla.getTotalVentaDiesel());

        turnoIsla.setNumeracionFinal(numeracionFInalJson);
        turnoIsla.setTotal(total);

        turnoIslaRepository.save(turnoIsla);

        return total;
    }

    public void cerrarTurnoIsla() {

        TurnoIsla turnoActivo = getTurnoActivo();

        calcularCuadre();
        turnoActivo.setActivo(false);
        turnoActivo.setFechaFinal(LocalDateTime.now());


        turnoIslaRepository.save(turnoActivo);
    }

    public Integer calcularCuadre() {
        TurnoIsla turnoIsla = getTurnoActivo();
        TurnoIslaResponse turnoIslaResponse = turnoIslaMapper.toResponseDto(turnoIsla);


        turnoIsla.setCuadre(
                 turnoIslaResponse.getTotalVentas() -
                turnoIslaResponse.getTotalTiros() -
                turnoIslaResponse.getTotalCreditos() -
                turnoIslaResponse.getTotalVisas() -
                turnoIslaResponse.getTotalGastos());

        turnoIslaRepository.save(turnoIsla);
        return turnoIsla.getCuadre();
    }



}
