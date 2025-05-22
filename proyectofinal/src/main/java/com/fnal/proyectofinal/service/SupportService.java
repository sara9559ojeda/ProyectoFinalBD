package com.fnal.proyectofinal.service;

import com.fnal.proyectofinal.entity.Support;
import com.fnal.proyectofinal.repository.SupportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    if (support.getTechnician() == null || support.getTechnician().getTechnicianId() == null) {
        throw new IllegalArgumentException("Support must be assigned to a valid technician.");
    }
    if (support.getClaim() == null || support.getClaim().getClaimId() == null) {
        throw new IllegalArgumentException("Support must be associated with a valid claim.");
    }
    if (support.getStartDate() != null && support.getStartDate().isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Support start date cannot be in the future.");
    }
    if (support.getEndDate() != null && support.getStartDate() != null &&
        support.getEndDate().isBefore(support.getStartDate())) {
        throw new IllegalArgumentException("Support end date cannot be before the start date.");
    }
    return supportRepository.save(support);
}

    public void deleteById(Long id) {
        supportRepository.deleteById(id);
    }

}
