package com.parqueadero.repositories;

import com.parqueadero.models.Combustible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CombustibleRepository extends JpaRepository<Combustible, Long> {
}
