package com.zynko.repositories.parqueadero;

import com.zynko.models.parqueadero.PagoParqueadero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoParqueaderoRepository extends JpaRepository<PagoParqueadero, Long> {

    @Query("SELECT p.estado FROM PagoParqueadero p WHERE p.id = :id")
    Boolean findEstadoById(@Param("id") Long id);
}
