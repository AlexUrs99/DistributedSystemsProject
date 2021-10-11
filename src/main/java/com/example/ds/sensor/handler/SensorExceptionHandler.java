package com.example.ds.sensor.handler;

import com.example.ds.client.exception.dto.EntityExceptionResponseDTO;
import com.example.ds.sensor.exception.SensorNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SensorExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({SensorNotFoundException.class})
    public ResponseEntity<EntityExceptionResponseDTO> handleSensorNotFoundException(SensorNotFoundException ex) {
        return new ResponseEntity<EntityExceptionResponseDTO>(
                new EntityExceptionResponseDTO(
                        ex.getMessage(),
                        "There is no sensor in the database at the specified ID."
                ), HttpStatus.NOT_FOUND);
    }
}
