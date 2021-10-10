package com.example.ds.device.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto {
    private Long id;
    private String name;
    private String description;
    private String location;
    private Integer averageConsumption;
    private Integer maxConsumption;
    private String clientName;
}
