package com.fnal.proyectofinal.controller;

import com.fnal.proyectofinal.entity.Technician;
import com.fnal.proyectofinal.service.TechnicianService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tecnicos")
public class TechnicianController {

    private final TechnicianService technicianService;

    public TechnicianController(TechnicianService technicianService) {
        this.technicianService = technicianService;
    }

    @GetMapping
    public List<Technician> getAllTechnicians() {
        return technicianService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Technician> getTechnicianById(@PathVariable("id") Long id) {
        return technicianService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Technician createTechnician(@RequestBody Technician technician) {
        return technicianService.save(technician);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Technician> updateTechnician(@PathVariable("id") Long id, @RequestBody Technician updatedTechnician) {
        return technicianService.findById(id).map(existing -> {
            updatedTechnician.setTechnicianId(id);
            return ResponseEntity.ok(technicianService.save(updatedTechnician));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnician(@PathVariable("id") Long id) {
        if (technicianService.findById(id).isPresent()) {
            technicianService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
