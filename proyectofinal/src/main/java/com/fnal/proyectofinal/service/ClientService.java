package com.fnal.proyectofinal.service;

import com.fnal.proyectofinal.entity.Client;
import com.fnal.proyectofinal.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public Client save(Client client) {
        if (client.getFullName() == null || client.getFullName().isBlank()) {
            throw new IllegalArgumentException("Client name cannot be empty.");
        }
        
        return clientRepository.save(client);
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }


}
