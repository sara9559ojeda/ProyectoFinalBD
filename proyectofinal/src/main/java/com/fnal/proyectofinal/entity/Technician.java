package com.fnal.proyectofinal.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tecnicos")
public class Technician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tecnico")
    private Long technicianId;

    @Column(name = "nombre_completo")
    private String fullName;

    @Column(name = "especialidad")
    private String specialty;

    @OneToMany(mappedBy = "technician")
    private List<Support> supportActions;
}

