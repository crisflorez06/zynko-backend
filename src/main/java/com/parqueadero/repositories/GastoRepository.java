package com.parqueadero.repositories;

import com.parqueadero.models.Gasto;
import com.parqueadero.models.Tiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {

    List<Gasto> findAllByTurnoIslaId(Long turnoIslaId);

}
