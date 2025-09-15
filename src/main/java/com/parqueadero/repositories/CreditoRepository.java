package com.parqueadero.repositories;

import com.parqueadero.models.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long> {
}
