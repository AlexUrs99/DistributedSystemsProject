package com.example.ds.client.converter;

import com.example.ds.client.dto.ClientDto;
import com.example.ds.client.model.Client;
import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class ClientConverter {

    public ClientDto convertToClientDTO(Client client) {
        return ClientDto.builder()
                .name(client.getName())
                .address(client.getAddress())
                .birthdate(client.getBirthdate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .role(client.getRole() == null ? "NORMAL_USER" : client.getRole().getName().name())
                .build();
    }
}