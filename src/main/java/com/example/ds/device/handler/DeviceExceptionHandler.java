package com.example.ds.device.handler;

import com.example.ds.client.exception.dto.EntityExceptionResponseDTO;
import com.example.ds.device.exception.DeviceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DeviceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DeviceNotFoundException.class})
    public ResponseEntity<EntityExceptionResponseDTO> handleDeviceNotFoundException(DeviceNotFoundException ex) {
        return new ResponseEntity<EntityExceptionResponseDTO>(
                new EntityExceptionResponseDTO(
                        ex.getMessage(),
                        "There is no device in the database at the specified ID."),
                HttpStatus.NOT_FOUND);
    }
}
