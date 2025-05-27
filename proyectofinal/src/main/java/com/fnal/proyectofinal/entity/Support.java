package com.fnal.proyectofinal.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "soportes")
public class Support {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_soporte")
    private Long idSupport;

    @ManyToOne
    @JoinColumn(name = "id_reclamo")
    private Claim claim;

    @Column(name = "fecha_fin")
    private LocalDate endDate;

    @Column(name = "fecha_inicio")
    private LocalDate startDate;

    @Column(name = "accion_tomada")
    private String actionTaken;

    @Column(name = "resultado")
    private String result;

    @ManyToOne
    @JoinColumn(name = "id_tecnico")

    private Technician technician;

}
