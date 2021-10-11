package com.example.ds.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorDto {
    private String name;
    private String timestamp;
    private Integer value;
    private String deviceName;
}
