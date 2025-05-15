package com.fnal.proyectofinal.repository;

import com.fnal.proyectofinal.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = """
        SELECT c.*, COUNT(r.id_reclamo) AS reclamos
        FROM clientes c
        JOIN ventas v   ON c.id_cliente = v.id_cliente
        JOIN reclamos r ON v.id_venta   = r.id_venta
        WHERE r.fecha_reclamo >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH)
        GROUP BY c.id_cliente
        ORDER BY reclamos DESC
        LIMIT 10
        """, nativeQuery = true)
    List<Object[]> findTopClientsWithMostClaimsLast6Months();
}
