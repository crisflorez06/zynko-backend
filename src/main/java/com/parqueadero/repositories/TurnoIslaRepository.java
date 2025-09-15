package com.parqueadero.repositories;

import com.parqueadero.models.TurnoIsla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TurnoIslaRepository extends JpaRepository<TurnoIsla, Long> {

    //este metodo trae el ultimo registro que haya en la tabla
    Optional<TurnoIsla> findTopByOrderByIdDesc();

    // Este método busca el único turno que no tiene fecha final. teniendo en cuenta que solo puede ver un turno activo
    //nos servira para intepretar el resultado como el ticket que esta abierto
    Optional<TurnoIsla> findTopByFechaFinalIsNullOrderByIdDesc();

}
