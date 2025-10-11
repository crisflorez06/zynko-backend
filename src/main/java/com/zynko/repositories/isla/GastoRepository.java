package com.zynko.repositories.isla;

import com.zynko.models.isla.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {

    List<Gasto> findAllByTurnoIslaId(Long turnoIslaId);

}
