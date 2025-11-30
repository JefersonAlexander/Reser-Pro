package com.microservice.booking.service;

import com.microservice.booking.entity.Client;
import com.microservice.booking.repository.ClientRepository;
import com.microservice.booking.DTO.ClientDTO;
import com.microservice.booking.mapper.ClientMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    private ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toDto)
                .toList();
    }

    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        return clientMapper.toDto(client);
    }

    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        client = clientRepository.save(client);
        return clientMapper.toDto(client);
    }


    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}