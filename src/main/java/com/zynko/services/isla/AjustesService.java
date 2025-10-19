package com.zynko.services.isla;

import com.zynko.models.isla.Ajustes;
import com.zynko.repositories.isla.AjustesRepository;
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
