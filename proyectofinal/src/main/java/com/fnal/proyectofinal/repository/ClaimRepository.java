package com.fnal.proyectofinal.repository;

import com.fnal.proyectofinal.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    // Consulta 1: Clientes con más reclamos en los últimos 6 meses
    @Query("""
            SELECT c.fullName, COUNT(cl) as totalReclamos
            FROM Client c
            JOIN c.sales s
            JOIN s.claims cl
            WHERE cl.claimDate >= :fechaLimite
            GROUP BY c.fullName
            ORDER BY totalReclamos DESC
            LIMIT 10
        """)
    List<Object[]> findTopClientsWithMostClaimsInLastSemester(@Param("fechaLimite") LocalDate fechaLimite);

    // Consulta 2: Productos con más reclamos (ahora a través de la venta)
    @Query("""
            SELECT p.name, COUNT(cl) as totalReclamos
            FROM Claim cl
            JOIN cl.sale s
            JOIN s.product p
            GROUP BY p.name
            ORDER BY totalReclamos DESC
            LIMIT 10
        """)
    List<Object[]> findProductsWithMostClaims();

    // Consulta 3: Reclamos resueltos en menos de 7 días
    @Query("""
            SELECT DISTINCT r
            FROM Claim r
            JOIN r.supportActions s
            WHERE s.startDate IS NOT NULL AND s.endDate IS NOT NULL
            AND DATEDIFF(s.endDate, s.startDate) < 7
        """)
    List<Claim> findClaimsResolvedUnderSevenDays();

    // Consulta 4: Tiempo promedio de resolución
    @Query(value = """
        SELECT AVG(DATEDIFF(s.fecha_fin, s.fecha_inicio)) AS avg_resolution_time
        FROM soportes s
        JOIN reclamos r ON s.id_reclamo = r.id_reclamo
        WHERE s.fecha_inicio IS NOT NULL
          AND s.fecha_fin IS NOT NULL
    """, nativeQuery = true)
    Double findAverageResolutionTime();
}