package com.example.ds.sensor.converter;

import com.example.ds.sensor.dto.SensorDto;
import com.example.ds.sensor.model.Sensor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SensorConverter {

    public static SensorDto convertToSensorDTO(Sensor sensor) {
        return SensorDto.builder()
                .name(sensor.getName())
                .timestamp(sensor.getTimestamp().toString())
                .value(sensor.getValue())
                .deviceName(sensor.getDevice() == null ? "Not currently assigned to any device." : sensor.getDevice().getName())
                .build();
    }
}
