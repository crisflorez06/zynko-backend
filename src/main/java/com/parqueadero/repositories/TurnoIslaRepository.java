package com.parqueadero.repositories;

import com.parqueadero.models.TurnoIsla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoIslaRepository extends JpaRepository<TurnoIsla, Long> {
}
