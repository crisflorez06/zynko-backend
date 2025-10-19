package com.zynko.services.parqueadero.ticket;

import java.time.LocalDateTime;

import com.zynko.models.parqueadero.TicketParqueadero;
import org.springframework.data.jpa.domain.Specification;

public class TicketParqueaderoSpecification {

    public static Specification<TicketParqueadero> hasCodigo(String codigo) {
        return (root, query, criteriaBuilder) -> {
            if (codigo == null || codigo.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("codigo")), "%" + codigo.toLowerCase() + "%");
        };
    }

    public static Specification<TicketParqueadero> hasPlaca(String placa) {
        return (root, query, criteriaBuilder) -> {
            if (placa == null || placa.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("vehiculo").get("placa")), "%" + placa.toLowerCase() + "%");
        };
    }

    public static Specification<TicketParqueadero> hasTipo(String tipo) {
        return (root, query, criteriaBuilder) -> {
            if (tipo == null || tipo.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("vehiculo").get("tipo")), tipo.toLowerCase());
        };
    }

    public static Specification<TicketParqueadero> hasUsuarioRecibio(String usuarioRecibio) {
        return (root, query, criteriaBuilder) -> {
            if (usuarioRecibio == null || usuarioRecibio.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("usuarioRecibio").get("nombre")), "%" + usuarioRecibio.toLowerCase() + "%");
        };
    }

    public static Specification<TicketParqueadero> hasUsuarioEntrego(String usuarioEntrego) {
        return (root, query, criteriaBuilder) -> {
            if (usuarioEntrego == null || usuarioEntrego.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("usuarioEntrego").get("nombre")), "%" + usuarioEntrego.toLowerCase() + "%");
        };
    }

    public static Specification<TicketParqueadero> hasParqueadero(String parqueadero) {
        return (root, query, criteriaBuilder) -> {
            if (parqueadero == null || parqueadero.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("parqueadero").get("nombre")), "%" + parqueadero.toLowerCase() + "%");
        };
    }

    public static Specification<TicketParqueadero> isPagado(Boolean pagado) {
        return (root, query, criteriaBuilder) -> {
            if (pagado == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("pago").get("estado"), pagado);
        };
    }

    public static Specification<TicketParqueadero> fechaEntradaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return (root, query, criteriaBuilder) -> {
            if (fechaInicio != null && fechaFin != null) {
                return criteriaBuilder.between(root.get("fechaHoraEntrada"), fechaInicio, fechaFin);
            } else if (fechaInicio != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("fechaHoraEntrada"), fechaInicio);
            } else if (fechaFin != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("fechaHoraEntrada"), fechaFin);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }
}
