package com.fnal.proyectofinal.repository;

import com.fnal.proyectofinal.entity.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long> {

    @Query(value = """
        SELECT t.nombre_completo,
               AVG(DATEDIFF(s.fecha_fin, s.fecha_inicio)) AS promedio_dias
        FROM tecnicos t
        JOIN soportes s ON t.id_tecnico = s.id_tecnico
        WHERE s.fecha_inicio IS NOT NULL
          AND s.fecha_fin    IS NOT NULL
        GROUP BY t.id_tecnico
        """, nativeQuery = true)
    List<Object[]> findAverageResolutionTimePerTechnician();
}

