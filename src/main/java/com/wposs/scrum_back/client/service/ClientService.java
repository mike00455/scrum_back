package com.wposs.scrum_back.client.service;

import com.wposs.scrum_back.area.entity.Area;
import com.wposs.scrum_back.client.entity.Client;
import com.wposs.scrum_back.client.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    public List<Client> getAll(){
        return clientRepository.findAll();
    }

    public Client save(Client client){
        return clientRepository.save(client);
    }

    public Optional<Client> findById(UUID clientId){
        return clientRepository.findById(clientId);
    }

    public Client updateClient(UUID id, Client client){
        return clientRepository.findById(id).map(client1 -> {
            client1.setClientName((client.getClientName()!=null)? client.getClientName() : client1.getClientName());
            return clientRepository.save(client1);
        }).orElse(null);
    }

    public Boolean existClientByName(String clientName){
        return clientRepository.existsByClientName(clientName);
    }

}