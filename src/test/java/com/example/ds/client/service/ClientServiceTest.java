package com.example.ds.client.service;

import com.example.ds.client.ClientFactory;
import com.example.ds.client.dto.ClientDto;
import com.example.ds.client.exception.ClientNotFoundException;
import com.example.ds.client.model.Client;
import com.example.ds.client.repository.ClientRepository;
import com.example.ds.role.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ClientServiceTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();
        clientRepository.saveAll(ClientFactory.generateListOfClients(5));
    }

    @Test
    void expectToReceiveTheListContainingAllTheClients() {
        List<ClientDto> allClients = clientService.getAllClients();

        assertEquals(allClients.size(), clientRepository.findAll().size());
    }

    @Test
    void expectToReceiveTheClientAtTheSpecifiedID() {
        Client savedClient = clientRepository.save(ClientFactory.generateClient());

        ClientDto returnedClient = clientService.getClientAtId(savedClient.getId().toString());

        assertEquals(savedClient.getAddress(), returnedClient.getAddress());
        assertEquals(savedClient.getName(), returnedClient.getName());
        assertEquals(savedClient.getBirthdate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), returnedClient.getBirthdate());
    }

    @Test
    void whenSavingAClientWithNoRole_ExpectTheRoleToBeNormalUserAsDefault() {
        ClientDto clientDto = ClientFactory.generateNormalUserClientDTO();

        clientDto.setRole(null);

        ClientDto savedClient = clientService.saveClient(clientDto);

        assertEquals("NORMAL_USER", savedClient.getRole());
        assertEquals(clientDto.getAddress(), savedClient.getAddress());
        assertEquals(clientDto.getName(), savedClient.getName());
        assertEquals(clientDto.getBirthdate(), savedClient.getBirthdate());
    }

    @Test
    void whenSearchingForAClientAtAnIDThatIsNotInTheDatabase_ExpectAClientNotFoundException() {
        assertThatThrownBy(() -> clientService.getClientAtId("999"))
                .isInstanceOf(ClientNotFoundException.class)
                .hasMessage("Couldn't find client at id: 999!");
    }

    @Test
    void whenAClientIsSaved_ExpectItToBeAddedToTheDatabase() {
        ClientDto body = ClientFactory.generateAdministratorClientDTO();
        ClientDto clientDTO = clientService.saveClient(body);

        Optional<Client> foundClient = clientRepository.findByName(clientDTO.getName());

        assertEquals(body.getBirthdate(), foundClient.get().getBirthdate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertEquals(body.getRole(), foundClient.get().getRole().getName().name());
        assertEquals(body.getName(), foundClient.get().getName());
        assertEquals(body.getAddress(), foundClient.get().getAddress());
    }

    @Test
    void whenEditingAClient_IfClientIDIsNotGood_ExceptClientNotFoundException() {
        ClientDto body = ClientFactory.generateNormalUserClientDTO();
        assertThatThrownBy(() -> clientService.editClient("999", body))
                .isInstanceOf(ClientNotFoundException.class)
                .hasMessage("Couldn't find client at id: 999!");
    }

    @Test
    void whenDeletingAClient_IfClientIDIsNotGood_ExceptClientNotFoundException() {
        assertThatThrownBy(() -> clientService.deleteClient("999"))
                .isInstanceOf(ClientNotFoundException.class)
                .hasMessage("Couldn't find client at id: 999!");
    }

    @Test
    void whenProvidingAClientBodyAndAnId_ExpectTheEditToHappenSuccessfully() {
        Client client = ClientFactory.generateClient();
        String savedClientID = clientRepository.save(client).getId().toString();

        ClientDto body = ClientFactory.generateAdministratorClientDTO();

        ClientDto editedClient = clientService.editClient(savedClientID, body);

        assertEquals(body, editedClient);
    }

    @Test
    void whenSuccessfullyDeletingAClient_ExpectThisMessage() {
        String savedClientID = clientRepository.save(ClientFactory.generateClient()).getId().toString();

        assertThat(clientService.deleteClient(savedClientID)).isEqualTo("Successfully deleted client at id: " + savedClientID + ".");
    }
}