package com.example.ds.role.handler;

import com.example.ds.client.exception.dto.EntityExceptionResponseDTO;
import com.example.ds.role.exception.ClientRoleNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ClientRoleExceptionHandlerTest {

    private final ClientRoleExceptionHandler exceptionHandler = new ClientRoleExceptionHandler();

    @Test
    void whenClientRoleNotFoundExceptionIsThrown_HandleItAndReturnNotFoundStatus() {
        ClientRoleNotFoundException ex = new ClientRoleNotFoundException("Couldn't find role NORMAL_USER in the database!");

        ResponseEntity<EntityExceptionResponseDTO> response = exceptionHandler.handleClientRoleNotFoundException(ex);

        Assertions.assertEquals("Couldn't find role NORMAL_USER in the database!", response.getBody().getExceptionMessage());
        Assertions.assertEquals("There is no role in the database with the specified name.", response.getBody().getHelperMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }
}