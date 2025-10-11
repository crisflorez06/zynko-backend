package com.zynko.repositories.isla;

import com.zynko.models.isla.CierreTurno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CierreTurnoRepository extends JpaRepository<CierreTurno, Long>, JpaSpecificationExecutor<CierreTurno> {
}
