package com.fnal.proyectofinal.service;

import com.fnal.proyectofinal.entity.Technician;
import com.fnal.proyectofinal.repository.TechnicianRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechnicianService {

    private final TechnicianRepository technicianRepository;

    public TechnicianService(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }

    // CRUD básico
    public List<Technician> findAll() {
        return technicianRepository.findAll();
    }

    public Optional<Technician> findById(Long id) {
        return technicianRepository.findById(id);
    }

    public Technician save(Technician technician) {
        return technicianRepository.save(technician);
    }

    public void deleteById(Long id) {
        technicianRepository.deleteById(id);
    }
}
