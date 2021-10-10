package com.example.ds.client.controller;

import com.example.ds.client.dto.ClientDto;
import com.example.ds.client.model.Client;
import com.example.ds.role.model.ERole;
import com.example.ds.role.model.Role;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ClientFactory {

    public static Client generateNormalUserClient() {
        return Client.builder()
                .id(1L)
                .name("RandomNormalUser")
                .birthdate(LocalDate.of(1999, 10, 28))
                .address("Address of Normal User, Romania")
                .role(Role.builder().name(ERole.NORMAL_USER).build())
                .build();
    }

    public static ClientDto generateNormalUserClientDTO() {
        return ClientDto.builder()
                .id(1L)
                .name("RandomNormalUser")
                .birthdate(LocalDate.of(1999, 10, 28).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .address("Address of Normal User, Romania")
                .role(ERole.NORMAL_USER.name())
                .build();
    }

    public static Client generateAdministratorClient() {
        return Client.builder()
                .id(1L)
                .name("RandomAdministrator")
                .birthdate(LocalDate.of(1999, 10, 28))
                .address("Address of Administrator, Romania")
                .role(Role.builder().name(ERole.ADMINISTRATOR).build())
                .build();
    }

    public static ClientDto generateAdministratorClientDTO() {
        return ClientDto.builder()
                .name("RandomAdministrator")
                .birthdate(LocalDate.of(1999, 10, 28).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .address("Address of Administrator, Romania")
                .role(ERole.ADMINISTRATOR.name())
                .build();
    }

    public static List<Client> generateListOfClients(int numberOfClients) {
        List<Client> clients = new ArrayList<>();

        for (int i = 0; i < numberOfClients; i++) {
            clients.add(
                    Client.builder()
                            .name("Client " + i)
                            .birthdate(LocalDate.of(1999, 10, 28))
                            .build()
            );
        }
        return clients;
    }

    public static List<ClientDto> generateListOfClientDTOs(int numberOfClients) {
        List<ClientDto> clients = new ArrayList<>();

        for (int i = 0; i < numberOfClients; i++) {
            clients.add(
                    ClientDto.builder()
                            .name("Client " + i)
                            .build()
            );
        }
        return clients;
    }
}
