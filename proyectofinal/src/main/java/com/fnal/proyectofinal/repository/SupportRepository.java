package com.fnal.proyectofinal.repository;

import com.fnal.proyectofinal.entity.Support;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface SupportRepository extends JpaRepository<Support, Long> {
@Query(value = """
    SELECT AVG(DATEDIFF(fecha_fin, fecha_inicio)) AS avg_resolution_time
    FROM soportes
    WHERE id_reclamo IS NOT NULL
      AND fecha_inicio IS NOT NULL
      AND fecha_fin IS NOT NULL
""", nativeQuery = true)
Double findAverageResolutionTimePerTechnician();
    

   
}
