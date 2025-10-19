package com.zynko.repositories.isla;

import com.zynko.models.isla.Ajustes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AjustesRepository extends JpaRepository<Ajustes, Long> {
    Optional<Ajustes> findFirstByOrderByIdAsc();
}
