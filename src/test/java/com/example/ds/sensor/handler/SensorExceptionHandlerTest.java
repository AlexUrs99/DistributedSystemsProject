package com.example.ds.sensor.handler;

import com.example.ds.client.exception.dto.EntityExceptionResponseDTO;
import com.example.ds.sensor.exception.SensorNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class SensorExceptionHandlerTest {

    private final SensorExceptionHandler exceptionHandler = new SensorExceptionHandler();

    @Test
    void whenSensorNotFoundException_HandleItAndExpectNotFoundStatus() {
        SensorNotFoundException ex = new SensorNotFoundException("Couldn't find sensor at id: 10!");
        ResponseEntity<EntityExceptionResponseDTO> response =
                exceptionHandler.handleSensorNotFoundException(ex);

        Assertions.assertEquals("Couldn't find sensor at id: 10!", response.getBody().getExceptionMessage());
        Assertions.assertEquals("There is no sensor in the database at the specified ID.", response.getBody().getHelperMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}