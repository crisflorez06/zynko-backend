package com.parqueadero.services;

import com.parqueadero.models.Ajustes;
import com.parqueadero.repositories.AjustesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AjustesService {

    private final AjustesRepository ajustesRepository;

    @Autowired
    public AjustesService(AjustesRepository ajustesRepository) {
        this.ajustesRepository = ajustesRepository;
    }

    public Integer getPrecioGasolina() {
        Ajustes ajustes = getAjustes();
        return ajustes.getPrecioVentaGasolina();
    }

    public Integer getPrecioDiesel() {
        Ajustes ajustes = getAjustes();
        return ajustes.getPrecioVentaDiesel();
    }

    private Ajustes getAjustes() {
        return ajustesRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new IllegalStateException("No se encontraron configuraciones de ajustes en la base de datos."));
    }
}