package com.example.ds.client.handler;

import com.example.ds.client.exception.ClientNotFoundException;
import com.example.ds.client.exception.dto.EntityExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ClientExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ClientNotFoundException.class})
    public ResponseEntity<EntityExceptionResponseDTO> handleClientNotFoundException(ClientNotFoundException ex) {
        return new ResponseEntity<EntityExceptionResponseDTO>(
                new EntityExceptionResponseDTO(
                        ex.getMessage(),
                        "There is no client in the database at the specified ID."),
                HttpStatus.NOT_FOUND);
    }
}
