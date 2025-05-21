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
        if (claim.getClaimDate() != null && claim.getClaimDate().isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("La fecha del reclamo no puede ser en el futuro.");
    }
     if (claim.getSale().getProduct() != null &&
        !claim.getSale().getProduct().getProductId().equals(claim.getProduct().getProductId())) {
        throw new IllegalArgumentException("El producto del reclamo no coincide con el de la venta.");
    }
        return claimRepository.save(claim);
    }

    public void deleteById(Long id) {
        claimRepository.deleteById(id);
    }

}
