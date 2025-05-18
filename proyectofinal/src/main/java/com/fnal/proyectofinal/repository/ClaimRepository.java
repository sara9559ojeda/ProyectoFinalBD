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

  @Query("""
          SELECT r
          FROM Claim r
          JOIN r.supportActions s
          WHERE s.startDate IS NOT NULL AND s.endDate IS NOT NULL
          AND DATEDIFF(s.endDate, s.startDate) < 7
      """)
  List<Claim> findClaimsResolvedUnderSevenDays();

}
