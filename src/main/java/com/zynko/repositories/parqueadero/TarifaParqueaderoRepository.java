package com.zynko.repositories.parqueadero;

import com.zynko.models.parqueadero.TarifaParqueadero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarifaParqueaderoRepository extends JpaRepository<TarifaParqueadero, Long> {

    TarifaParqueadero findByTipoVehiculo(String tipoVehiculo);

    @Query("SELECT t.precioDia FROM TarifaParqueadero t WHERE t.tipoVehiculo = :tipoVehiculo")
    Integer findPrecioByTipoVehiculo(String tipoVehiculo);

    @Query("SELECT DISTINCT t.tipoVehiculo FROM TarifaParqueadero t")
    List<String> findDistinctTipoVehiculo();
}
