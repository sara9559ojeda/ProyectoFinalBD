package com.fnal.proyectofinal.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
@Table(name = "clientes")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long clientId;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "telefono")
    private String phone;

    @Column(name = "numero_identidad")
    private String identificationNumber;

    @Column(name = "direccion")
    private String address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Sale> sales;
}
