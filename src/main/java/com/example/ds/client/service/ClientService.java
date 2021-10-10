package com.example.ds.client.service;

import com.example.ds.client.converter.ClientConverter;
import com.example.ds.client.dto.ClientDto;
import com.example.ds.client.exception.ClientNotFoundException;
import com.example.ds.client.model.Client;
import com.example.ds.client.repository.ClientRepository;
import com.example.ds.role.exception.ClientRoleNotFoundException;
import com.example.ds.role.model.ERole;
import com.example.ds.role.model.Role;
import com.example.ds.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;

    public List<ClientDto> getAllClients() {
        return clientRepository.findAll().stream()
                .map(ClientConverter::convertToClientDTO)
                .collect(Collectors.toList());
    }

    public ClientDto getClientAtId(String id) {
        return ClientConverter.convertToClientDTO((
                clientRepository.findById(Long.parseLong(id))
                        .orElseThrow(() -> new ClientNotFoundException("Couldn't find client at id: " + id + "!"))
        ));
    }

    public ClientDto saveClient(ClientDto body) {
        Role role = roleRepository.findByName(ERole.valueOf(body.getRole()))
                .orElseThrow(() -> new ClientRoleNotFoundException("Couldn't find role: " + body.getRole().toUpperCase() + "!"));

        Client builtClient = Client.builder()
                .name(body.getName())
                .birthdate(LocalDate.parse(body.getBirthdate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .address(body.getAddress())
                .role(role)
                .build();

        Client savedUser = clientRepository.save(builtClient);

        return ClientConverter.convertToClientDTO(savedUser);
    }

    public ClientDto editClient(String id, ClientDto body) {
        Client foundClient = clientRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ClientNotFoundException("Couldn't find client at id: " + id + "!"));

        Client editedClient = updateClientWithNewBody(foundClient, body);

        return ClientConverter.convertToClientDTO(editedClient);
    }

    public String deleteClient(String id) {
        try {
            clientRepository.deleteById(Long.parseLong(id));
            return "Deleted client at id: " + id + ".";
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Client updateClientWithNewBody(Client client, ClientDto body) {
        Role role = roleRepository.findByName(ERole.valueOf(body.getRole()))
                .orElseThrow(() -> new ClientRoleNotFoundException("Couldn't find role: " + body.getRole().toUpperCase() + "!"));

        client.setRole(role);
        client.setAddress(body.getAddress());
        client.setBirthdate(LocalDate.parse(body.getBirthdate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        client.setName(body.getName());

        return clientRepository.save(client);
    }
}
