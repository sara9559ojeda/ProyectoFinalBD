package com.fnal.proyectofinal.repository;

import com.fnal.proyectofinal.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    @Query(value = """
        SELECT DISTINCT r.*
        FROM reclamos r
        JOIN soportes s ON r.id_reclamo = s.id_reclamo
        WHERE s.fecha_inicio IS NOT NULL
          AND s.fecha_fin    IS NOT NULL
          AND DATEDIFF(s.fecha_fin, s.fecha_inicio) < 7
        """, nativeQuery = true)
    List<Claim> findClaimsResolvedInLessThan7Days();
}
