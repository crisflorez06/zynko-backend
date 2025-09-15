package com.parqueadero.repositories;

import com.parqueadero.models.Manguera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MangueraRepository extends JpaRepository<Manguera, Long> {
}
