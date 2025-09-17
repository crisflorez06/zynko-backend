package com.parqueadero.repositories;

import com.parqueadero.models.Tiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TiroRepository extends JpaRepository<Tiro, Long> {

    List<Tiro> findAllByTurnoIslaId(Long turnoIslaId);

    Optional<Tiro> findTopByTurnoIslaIdOrderByIdDesc(Long turnoIslaId);
}
