package com.fnal.proyectofinal.repository;

import com.fnal.proyectofinal.entity.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportRepository extends JpaRepository<Support, Long> {

    @Query(value = """
        SELECT t.nombre_completo,
               AVG(DATEDIFF(s.fecha_fin, s.fecha_inicio)) AS promedio_dias
        FROM soportes s
        JOIN tecnicos t ON s.id_tecnico = t.id_tecnico
        WHERE s.fecha_inicio IS NOT NULL AND s.fecha_fin IS NOT NULL
        GROUP BY t.nombre_completo
        """, nativeQuery = true)
    List<Object[]> findAverageResolutionTimeByTechnician();
}
