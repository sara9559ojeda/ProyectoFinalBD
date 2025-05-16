package com.fnal.proyectofinal.service;

import com.fnal.proyectofinal.entity.Claim;
import com.fnal.proyectofinal.repository.ClaimRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClaimService {

    private final ClaimRepository claimRepository;

    public ClaimService(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    // CRUD básico
    public List<Claim> findAll() {
        return claimRepository.findAll();
    }

    public Optional<Claim> findById(Long id) {
        return claimRepository.findById(id);
    }

    public Claim save(Claim claim) {
        return claimRepository.save(claim);
    }

    public void deleteById(Long id) {
        claimRepository.deleteById(id);
    }

    // Consulta avanzada
    public List<Claim> getClaimsResolvedInLessThan7Days() {
        return claimRepository.findClaimsResolvedInLessThan7Days();
    }
}
