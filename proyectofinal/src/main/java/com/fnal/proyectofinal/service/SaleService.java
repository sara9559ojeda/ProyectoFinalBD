package com.fnal.proyectofinal.service;

import com.fnal.proyectofinal.entity.Sale;
import com.fnal.proyectofinal.repository.ClientRepository;
import com.fnal.proyectofinal.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;

    public SaleService(SaleRepository saleRepository, ClientRepository clientRepository) {
        this.saleRepository = saleRepository;
        this.clientRepository = clientRepository;
    }

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public Optional<Sale> findById(Long id) {
        return saleRepository.findById(id);
    }

    public Sale save(Sale sale) {
        if (sale.getClient() == null || sale.getClient().getClientId() == null) {
            throw new IllegalArgumentException("Sale must be associated with a valid client.");
        }
        boolean clientExists = clientRepository.existsById(sale.getClient().getClientId());
        if (!clientExists) {
            throw new IllegalArgumentException("Client does not exist.");
        }
        if (sale.getProduct() == null || sale.getProduct().getProductId() == null) {
            throw new IllegalArgumentException("Sale must be associated with a valid product.");
        }
        if (sale.getSaleDate() != null && sale.getSaleDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Sale date cannot be in the future.");
        }
        return saleRepository.save(sale);
    }

    public void deleteById(Long id) {
        saleRepository.deleteById(id);
    }
}
