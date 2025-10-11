package com.parqueadero.config;

import com.parqueadero.models.Ajustes;
import com.parqueadero.models.Tarifa;
import com.parqueadero.models.Usuario;
import com.parqueadero.models.Lavador;
import com.parqueadero.repositories.AjustesRepository;
import com.parqueadero.repositories.LavadorRepository;
import com.parqueadero.repositories.TarifaRepository;
import com.parqueadero.repositories.UsuarioRepository;
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
    private final TarifaRepository tarifaRepository;
    private final LavadorRepository lavadorRepository;

    public DatosInicialesRunner(
            AjustesRepository ajustesRepository,
            UsuarioRepository usuarioRepository,
            TarifaRepository tarifaRepository,
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

        tarifaRepository.save(new Tarifa("Moto", 6000));
        tarifaRepository.save(new Tarifa("Carro", 8000));
        tarifaRepository.save(new Tarifa("Camioneta", 10000));
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
