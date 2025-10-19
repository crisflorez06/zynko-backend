package com.zynko.repositories.parqueadero;

import com.zynko.models.parqueadero.LugarParqueadero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LugarParqueaderoRepository extends JpaRepository<LugarParqueadero, Long> {
    Optional<LugarParqueadero> findByNombre(String nombre);

    @Query("SELECT DISTINCT p.nombre FROM LugarParqueadero p")
    List<String> findDistinctNombres();
}
