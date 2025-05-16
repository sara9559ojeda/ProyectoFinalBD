package com.fnal.proyectofinal.service;

import com.fnal.proyectofinal.entity.Support;
import com.fnal.proyectofinal.repository.SupportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupportService {

    private final SupportRepository supportRepository;

    public SupportService(SupportRepository supportRepository) {
        this.supportRepository = supportRepository;
    }

    public List<Support> findAll() {
        return supportRepository.findAll();
    }

    public Optional<Support> findById(Long id) {
        return supportRepository.findById(id);
    }

    public Support save(Support support) {
        return supportRepository.save(support);
    }

    public void deleteById(Long id) {
        supportRepository.deleteById(id);
    }
    public List<Object[]> getAverageResolutionTimeByTechnician() {
        return supportRepository.findAverageResolutionTimeByTechnician();
    }
}
