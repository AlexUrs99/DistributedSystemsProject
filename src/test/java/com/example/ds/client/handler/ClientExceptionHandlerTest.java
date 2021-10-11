package com.example.ds.client.handler;

import com.example.ds.client.exception.ClientNotFoundException;
import com.example.ds.client.exception.dto.EntityExceptionResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientExceptionHandlerTest {

    private final ClientExceptionHandler exceptionHandler = new ClientExceptionHandler();

    @Test
    void whenClientNotFoundExceptionIsThrown_ExpectNotFoundStatus() {
        ClientNotFoundException ex = new ClientNotFoundException("Couldn't find client at id: 10!");
        ResponseEntity<EntityExceptionResponseDTO> response = exceptionHandler.handleClientNotFoundException(ex);

        assertEquals("There is no client in the database at the specified ID.", response.getBody().getHelperMessage());
        assertEquals("Couldn't find client at id: 10!", response.getBody().getExceptionMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }
}