package com.parqueadero.repositories;

import com.parqueadero.models.Lavado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LavadoRepository extends JpaRepository<Lavado, Long> {

    List<Lavado> findAllByTurnoIslaIdOrderByFechaRegistroDesc(Long turnoIslaId);

    List<Lavado> findByTurnoIslaIdAndFechaRegistroBetween(Long turnoIslaId, LocalDateTime inicio, LocalDateTime fin);

    List<Lavado> findByFechaRegistroBetweenOrderByFechaRegistroDesc(LocalDateTime inicio, LocalDateTime fin);
}
