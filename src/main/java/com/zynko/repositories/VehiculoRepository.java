package com.zynko.repositories;

import com.zynko.models.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    boolean existsByPlaca(String placa);
    Optional<Vehiculo> findByPlaca(String placa);
}
