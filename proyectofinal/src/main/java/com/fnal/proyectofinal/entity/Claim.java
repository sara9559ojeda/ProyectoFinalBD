package com.fnal.proyectofinal.entity;

import java.time.LocalDate;
import java.util.List;

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
    @JoinColumn(name = "id_venta")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Product product;

    @Column(name = "fecha_reclamo")
    private LocalDate claimDate;

    @Column(name = "tipo")
    private String type;

    @Column(name = "estado")
    private String status;

    @OneToMany(mappedBy = "claim", cascade = CascadeType.ALL)
    private List<Support> supportActions;
}
