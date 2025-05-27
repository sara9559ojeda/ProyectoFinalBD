package com.fnal.proyectofinal.controller;

import com.fnal.proyectofinal.entity.Claim;
import com.fnal.proyectofinal.repository.ClaimRepository;
import com.fnal.proyectofinal.service.ClaimService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reclamos")
public class ClaimController {

    private final ClaimService claimService;
    private final ClaimRepository claimRepository;

    public ClaimController(ClaimService claimService, ClaimRepository claimRepository) {
        this.claimService = claimService;
        this.claimRepository = claimRepository;
    }

    @GetMapping
    public List<Claim> getAllClaims() {
        return claimService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Claim> getClaimById(@PathVariable("id") Long id) {
        return claimService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Claim createClaim(@RequestBody Claim claim) {
        return claimService.save(claim);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Claim> updateClaim(@PathVariable("id") Long id, @RequestBody Claim updatedClaim) {
        return claimService.findById(id).map(existing -> {
            updatedClaim.setClaimId(id);
            return ResponseEntity.ok(claimService.save(updatedClaim));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClaim(@PathVariable("id") Long id) {
        if (claimService.findById(id).isPresent()) {
            claimService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/consultas/clientes-top")
    public List<Map<String, Object>> getClientesTop() {
        LocalDate fechaLimite = LocalDate.now().minusMonths(6);
        List<Object[]> resultados = claimRepository.findTopClientsWithMostClaimsInLastSemester(fechaLimite);

        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] fila : resultados) {
            Map<String, Object> map = new HashMap<>();
            map.put("cliente", fila[0]);
            map.put("totalReclamos", fila[1]);
            response.add(map);
        }
        return response;
    }

    @GetMapping("/consultas/productos-top")
    public List<Map<String, Object>> productosConMasReclamos() {
        List<Object[]> resultados = claimRepository.findProductsWithMostClaims();

        List<Map<String, Object>> respuesta = new ArrayList<>();
        for (Object[] fila : resultados) {
            Map<String, Object> map = new HashMap<>();
            map.put("nombreProducto", fila[0]);
            map.put("totalReclamos", fila[1]);
            respuesta.add(map);
        }
        return respuesta;
    }

    @GetMapping("/consultas/resueltos-rapido")
    public ResponseEntity<List<Claim>> getClaimsResolvedInLessThan7Days() {
        List<Claim> claims = claimRepository.findClaimsResolvedUnderSevenDays();
        if (claims.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(claims);
    }

    @GetMapping("/consultas/tiempo-promedio-resolucion")
    public ResponseEntity<Double> getAverageResolutionTime() {
        Double avgDays = claimRepository.findAverageResolutionTime();
        return ResponseEntity.ok(avgDays != null ? avgDays : 0.0);
    }
}