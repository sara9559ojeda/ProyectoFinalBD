package com.fnal.proyectofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fnal.proyectofinal.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    
}
