package com.zynko.repositories.parqueadero;

import com.zynko.models.parqueadero.TicketParqueadero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketParqueaderoRepository extends JpaRepository<TicketParqueadero, Long>, JpaSpecificationExecutor<TicketParqueadero> {

    // Busca un ticket por su código de barras QR
    TicketParqueadero findByCodigo(String codigoBarras);

    /**
     * Obtiene TODOS los tickets financieramente relevantes para un turno.
     * Un ticket es relevante si:
     * 1. El vehículo ENTRÓ durante el turno.
     * 2. El vehículo SALIÓ durante el turno.
     * 3. El PAGO se realizó durante el turno (clave para mensualidades).
     * Se usa JOIN FETCH para optimizar y traer todas las entidades relacionadas.
     */
    @Query("SELECT DISTINCT t FROM TicketParqueadero t " +
            "LEFT JOIN FETCH t.vehiculo " +
            "LEFT JOIN FETCH t.lugarParqueadero " +
            "LEFT JOIN FETCH t.pagoParqueadero p " +
            "WHERE (t.fechaHoraEntrada BETWEEN :fechaInicio AND :fechaFin) " +
            "OR (t.fechaHoraSalida BETWEEN :fechaInicio AND :fechaFin) " +
            "OR (p.fechaHora BETWEEN :fechaInicio AND :fechaFin)")
    List<TicketParqueadero> findTicketsParaCierre(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );

    /**
     * Obtiene todos los tickets de vehículos que están actualmente dentro del parqueadero.
     * Es necesaria para el "inventario" de vehículos restantes.
     */
    @Query("SELECT t FROM TicketParqueadero t " +
            "LEFT JOIN FETCH t.vehiculo " +
            "LEFT JOIN FETCH t.lugarParqueadero " +
            "WHERE t.pagoParqueadero.estado = false")
    List<TicketParqueadero> findTicketsAbiertos();

}
