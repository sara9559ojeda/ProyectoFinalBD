package com.fnal.proyectofinal.repository;

import com.fnal.proyectofinal.entity.Technician;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;



@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long> {

  
  }

