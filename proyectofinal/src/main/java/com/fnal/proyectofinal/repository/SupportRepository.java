package com.fnal.proyectofinal.repository;

import com.fnal.proyectofinal.entity.Support;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;



@Repository
public interface SupportRepository extends JpaRepository<Support, Long> {

    

   
}
