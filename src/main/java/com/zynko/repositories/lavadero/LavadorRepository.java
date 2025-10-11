package com.zynko.repositories.lavadero;

import com.zynko.models.lavadero.Lavador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LavadorRepository extends JpaRepository<Lavador, Long> {

    Optional<Lavador> findByNombreIgnoreCase(String nombre);

    @Query("SELECT l.nombre FROM Lavador l ORDER BY l.nombre ASC")
    List<String> findAllNombresOrderByNombre();
}
