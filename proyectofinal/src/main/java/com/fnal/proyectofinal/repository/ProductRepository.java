package com.fnal.proyectofinal.repository;

import com.fnal.proyectofinal.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
        SELECT p.*, COUNT(r.id_reclamo) AS total_reclamos
        FROM productos p
        JOIN reclamos r ON p.id_producto = r.id_producto
        GROUP BY p.id_producto
        ORDER BY total_reclamos DESC
        LIMIT 10
        """, nativeQuery = true)
    List<Object[]> findTopProductsWithMostClaims();
}
