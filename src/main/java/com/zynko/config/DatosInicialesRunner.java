package com.zynko.config;

import com.zynko.models.isla.Ajustes;
import com.zynko.models.parqueadero.TarifaParqueadero;
import com.zynko.models.Usuario;
import com.zynko.models.lavadero.Lavador;
import com.zynko.repositories.isla.AjustesRepository;
import com.zynko.repositories.lavadero.LavadorRepository;
import com.zynko.repositories.parqueadero.TarifaParqueaderoRepository;
import com.zynko.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Inicializa datos base para que la aplicación tenga valores mínimos
 * cuando se crea una nueva base de datos.
 */
@Component
public class DatosInicialesRunner implements CommandLineRunner {

    private final AjustesRepository ajustesRepository;
    private final UsuarioRepository usuarioRepository;
    private final TarifaParqueaderoRepository tarifaRepository;
    private final LavadorRepository lavadorRepository;

    public DatosInicialesRunner(
            AjustesRepository ajustesRepository,
            UsuarioRepository usuarioRepository,
            TarifaParqueaderoRepository tarifaRepository,
            LavadorRepository lavadorRepository
    ) {
        this.ajustesRepository = ajustesRepository;
        this.usuarioRepository = usuarioRepository;
        this.tarifaRepository = tarifaRepository;
        this.lavadorRepository = lavadorRepository;
    }

    @Override
    public void run(String... args) {
        inicializarAjustes();
        inicializarUsuarioAdmin();
        inicializarTarifas();
        inicializarLavadores();
    }

    private void inicializarAjustes() {
        if (ajustesRepository.count() > 0) {
            return;
        }

        Ajustes ajustes = new Ajustes();
        ajustes.setPrecioVentaDiesel(10500);
        ajustes.setPrecioVentaGasolina(11500);
        ajustesRepository.save(ajustes);
    }

    private void inicializarUsuarioAdmin() {
        if (usuarioRepository.count() > 0) {
            return;
        }

        Usuario admin = new Usuario();
        admin.setNombre("admin");
        admin.setCedula("123");
        admin.setFechaInicioSesion(null);
        usuarioRepository.save(admin);
    }

    private void inicializarTarifas() {
        if (tarifaRepository.count() > 0) {
            return;
        }

        tarifaRepository.save(new TarifaParqueadero("Moto", 6000));
        tarifaRepository.save(new TarifaParqueadero("Carro", 8000));
        tarifaRepository.save(new TarifaParqueadero("Camioneta", 10000));
    }

    private void inicializarLavadores() {
        if (lavadorRepository.count() > 0) {
            return;
        }

        lavadorRepository.save(new Lavador(null, "Sin asignar"));
        lavadorRepository.save(new Lavador(null, "Pedro Pérez"));
        lavadorRepository.save(new Lavador(null, "María Gómez"));
    }
}
