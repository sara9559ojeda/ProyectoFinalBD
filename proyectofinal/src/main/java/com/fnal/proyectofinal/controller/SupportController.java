package com.fnal.proyectofinal.controller;

import com.fnal.proyectofinal.entity.Support;
import com.fnal.proyectofinal.repository.SupportRepository;
import com.fnal.proyectofinal.service.SupportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/soportes")
public class SupportController {

    private final SupportService supportService;
    private final SupportRepository supportRepository;
   

    public SupportController(SupportService supportService, SupportRepository supportRepository) {
        this.supportService = supportService;
        this.supportRepository = supportRepository;
       
    }

    @GetMapping
    public List<Support> getAllSupportActions() {
        return supportService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Support> getSupportById(@PathVariable("id") Long id) {
        return supportService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Support createSupport(@RequestBody Support support) {
        return supportService.save(support);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Support> updateSupport(@PathVariable("id") Long id, @RequestBody Support updatedSupport) {
        return supportService.findById(id).map(existing -> {
            updatedSupport.setIdSupport(id);
            return ResponseEntity.ok(supportService.save(updatedSupport));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupport(@PathVariable("id") Long id) {
        if (supportService.findById(id).isPresent()) {
            supportService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    


   @GetMapping("/avg-resolution-time")
public ResponseEntity<Double> getGlobalAverageResolutionTime() {
    Double avgDays = supportRepository.findAverageResolutionTimePerTechnician();
    return ResponseEntity.ok(avgDays);
}
}
