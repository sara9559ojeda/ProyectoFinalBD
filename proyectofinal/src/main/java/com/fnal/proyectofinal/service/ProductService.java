package com.fnal.proyectofinal.service;

import com.fnal.proyectofinal.entity.Product;
import com.fnal.proyectofinal.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
     if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty.");
        }
        if (product.getPrice() == null || product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price must be a non-negative value.");
        }
        if (product.getWarranty() == null || product.getWarranty().trim().isEmpty()) {
            throw new IllegalArgumentException("Product warranty cannot be null or empty.");
        }
        if (product.getDescription() == null || product.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Product description cannot be null or empty.");
            
        }
       
    return productRepository.save(product);
}


    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}

