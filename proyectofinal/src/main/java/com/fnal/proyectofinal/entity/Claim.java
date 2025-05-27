package com.fnal.proyectofinal.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "reclamos")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reclamo")
    private Long claimId;

    @ManyToOne
    @JoinColumn(name = "id_venta", nullable = false)
    private Sale sale;

    @Column(name = "fecha_reclamo")
    private LocalDate claimDate;

    @Column(name = "tipo")
    private String type;

    @Column(name = "estado")
    private String status;
    
    @Column(name = "descripcion")
    private String description;

    @OneToMany(mappedBy = "claim", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Support> supportActions;
}