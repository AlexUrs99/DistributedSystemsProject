package com.example.ds.sensor.dto;

import lombok.Data;

@Data
public class SensorDto {
    private Long id;
    private String timestamp;
    private Integer value;
    private String deviceName;
}
