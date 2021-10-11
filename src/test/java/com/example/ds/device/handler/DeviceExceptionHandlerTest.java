package com.example.ds.device.handler;

import com.example.ds.client.exception.dto.EntityExceptionResponseDTO;
import com.example.ds.device.exception.DeviceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeviceExceptionHandlerTest {

    private final DeviceExceptionHandler exceptionHandler = new DeviceExceptionHandler();

    @Test
    void whenDeviceNotFoundExceptionIsThrown_HandleItAndExpectNotFoundStatus() {
        DeviceNotFoundException ex = new DeviceNotFoundException("Couldn't find device at id: 10!");
        ResponseEntity<EntityExceptionResponseDTO> response = exceptionHandler.handleDeviceNotFoundException(ex);

        assertEquals("There is no device in the database at the specified ID.", response.getBody().getHelperMessage());
        assertEquals("Couldn't find device at id: 10!", response.getBody().getExceptionMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }
}