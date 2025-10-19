package com.zynko.repositories.isla;

import com.zynko.models.isla.Tiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TiroRepository extends JpaRepository<Tiro, Long> {

    List<Tiro> findAllByTurnoIslaId(Long turnoIslaId);

    //este metodo se crea para saber el numero del tiro en el turno
    Optional<Tiro> findTopByTurnoIslaIdOrderByIdDesc(Long turnoIslaId);
}
