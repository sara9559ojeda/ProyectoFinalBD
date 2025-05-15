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
@Table(name = "ventas")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long saleId;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Product product;

    @Column(name = "fecha_venta")
    private LocalDate saleDate;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<Claim> claims;
}
