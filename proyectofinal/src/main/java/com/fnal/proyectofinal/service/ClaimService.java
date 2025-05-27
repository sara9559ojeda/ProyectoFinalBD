package com.fnal.proyectofinal.service;

import com.fnal.proyectofinal.entity.Claim;
import com.fnal.proyectofinal.repository.ClaimRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClaimService {

    private final ClaimRepository claimRepository;

    public ClaimService(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    public List<Claim> findAll() {
        return claimRepository.findAll();
    }

    public Optional<Claim> findById(Long id) {
        return claimRepository.findById(id);
    }

    public Claim save(Claim claim) {
        // Validar que la fecha del reclamo no sea futura
        if (claim.getClaimDate() != null && claim.getClaimDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha del reclamo no puede ser en el futuro.");
        }
        
        // Validar que el reclamo tenga una venta asociada
        if (claim.getSale() == null || claim.getSale().getSaleId() == null) {
            throw new IllegalArgumentException("El reclamo debe estar asociado a una venta.");
        }
        
        // Validar que la fecha del reclamo sea posterior a la fecha de venta
        if (claim.getSale().getSaleDate() != null && claim.getClaimDate() != null 
            && claim.getClaimDate().isBefore(claim.getSale().getSaleDate())) {
            throw new IllegalArgumentException("La fecha del reclamo debe ser posterior a la fecha de venta.");
        }
        
        return claimRepository.save(claim);
    }

    public void deleteById(Long id) {
        claimRepository.deleteById(id);
    }
}