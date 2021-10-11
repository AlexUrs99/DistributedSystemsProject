package com.example.ds.client.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EntityExceptionResponseDTO {
    private String exceptionMessage;
    private String helperMessage;
}
