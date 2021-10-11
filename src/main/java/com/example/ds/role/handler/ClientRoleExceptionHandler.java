package com.example.ds.role.handler;

import com.example.ds.client.exception.dto.EntityExceptionResponseDTO;
import com.example.ds.role.exception.ClientRoleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ClientRoleExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ClientRoleNotFoundException.class})
    public ResponseEntity<EntityExceptionResponseDTO> handleClientRoleNotFoundException(ClientRoleNotFoundException ex) {
        return new ResponseEntity<EntityExceptionResponseDTO>(
                new EntityExceptionResponseDTO(
                        ex.getMessage(),
                        "There is no role in the database with the specified name."),
                HttpStatus.NOT_FOUND);
    }
}
