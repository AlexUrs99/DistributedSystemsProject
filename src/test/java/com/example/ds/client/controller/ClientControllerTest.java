package com.example.ds.client.controller;

import com.example.ds.client.dto.ClientDto;
import com.example.ds.client.model.Client;
import com.example.ds.client.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClientControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    void whenGetAllClientsIsCalled_ExpectTheListOfClientDTOs() throws Exception {
        List<ClientDto> clients = ClientFactory.generateListOfClientDTOs(10);

        when(clientService.getAllClients()).thenReturn(clients);

        mockMvc.perform(get("/clients")
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        verify(clientService, times(1)).getAllClients();

        assertThat(10).isEqualTo(clients.size());
    }

    @Test
    void whenSearchingForAClientAtSpecificID_ExpectThatSpecificClient() throws Exception {
        Client client = ClientFactory.generateNormalUserClient();
        ClientDto clientDto = ClientFactory.generateNormalUserClientDTO();
        String clientID = client.getId().toString();

        when(clientService.getClientAtId(clientID)).thenReturn(clientDto);

        mockMvc.perform(get("/clients" + "/" + clientID)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("RandomNormalUser"))
                .andExpect(jsonPath("$.birthdate").value("28-10-1999"))
                .andExpect(jsonPath("$.address").value("Address of Normal User, Romania"))
                .andExpect(jsonPath("$.role").value("NORMAL_USER"));

        verify(clientService, times(1)).getClientAtId(client.getId().toString());
    }

    @Test
    void whenSavingANewClient_ShouldReturnItsCorrespondingDTO() throws Exception {
        ClientDto clientDto = ClientFactory.generateNormalUserClientDTO();

        when(clientService.saveClient(clientDto)).thenReturn(clientDto);

        mockMvc.perform(post("/clients")
                        .content(objectMapper.writeValueAsString(clientDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("RandomNormalUser"))
                .andExpect(jsonPath("$.birthdate").value("28-10-1999"))
                .andExpect(jsonPath("$.address").value("Address of Normal User, Romania"))
                .andExpect(jsonPath("$.role").value("NORMAL_USER"));

        verify(clientService, times(1)).saveClient(clientDto);
    }

    @Test
    void whenEditingAUser_ExpectTheUserToReceiveTheUpdate() throws Exception {
        Client client = ClientFactory.generateNormalUserClient();
        ClientDto clientDto = ClientFactory.generateNormalUserClientDTO();
        String clientID = client.getId().toString();

        when(clientService.editClient(clientID, clientDto)).thenReturn(clientDto);


        mockMvc.perform(put("/clients" + "/" + clientID)
                        .content(objectMapper.writeValueAsString(clientDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("RandomNormalUser"))
                .andExpect(jsonPath("$.birthdate").value("28-10-1999"))
                .andExpect(jsonPath("$.address").value("Address of Normal User, Romania"))
                .andExpect(jsonPath("$.role").value("NORMAL_USER"));

        verify(clientService, times(1)).editClient(clientID, clientDto);
    }

    @Test
    void whenDeletingClientWithValidID_ShouldReturnOkStatus() throws Exception {
        Client client = ClientFactory.generateNormalUserClient();
        String clientID = client.getId().toString();

        when(clientService.deleteClient(clientID)).thenReturn("Deleted client at id: " + clientID + ".");

        mockMvc.perform(delete("/clients/" + "/" + clientID))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted client at id: " + clientID + "."));

        verify(clientService, times(1)).deleteClient(clientID);
    }
}