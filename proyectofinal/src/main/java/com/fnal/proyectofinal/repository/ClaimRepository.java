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

  @Query("""
          SELECT c.fullName, COUNT(cl) as totalReclamos
          FROM Client c
          JOIN c.sales s
          JOIN s.claims cl
          WHERE cl.claimDate >= :fechaLimite
          GROUP BY c.fullName
          ORDER BY totalReclamos DESC
      """)
  List<Object[]> findTopClientsWithMostClaimsInLastSemester(@Param("fechaLimite") LocalDate fechaLimite);

  @Query("""
          SELECT p.name, COUNT(cl) as totalReclamos
          FROM Claim cl
          JOIN cl.product p
          GROUP BY p.name
          ORDER BY totalReclamos DESC
      """)
  List<Object[]> findProductsWithMostClaims();

 /* @Query(value = """
      SELECT DISTINCT r.*
      FROM reclamos r
      JOIN soportes s ON r.id_reclamo = s.id_reclamo
      WHERE s.fecha_inicio IS NOT NULL
        AND s.fecha_fin    IS NOT NULL
        AND DATEDIFF(s.fecha_fin, s.fecha_inicio) < 7
      """, nativeQuery = true)
  List<Claim> findClaimsResolvedInLessThan7Days();  */
}
