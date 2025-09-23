package com.parqueadero.repositories;

import com.parqueadero.models.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long> {

    List<Credito> findAllByTurnoIslaId(Long turnoIslaId);
}
