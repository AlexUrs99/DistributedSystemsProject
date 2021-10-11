package com.example.ds.sensor.converter;

import com.example.ds.sensor.dto.SensorDto;
import com.example.ds.sensor.model.Sensor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SensorConverter {

    public static SensorDto convertToSensorDTO(Sensor sensor) {
        return SensorDto.builder()
                .timestamp(sensor.getTimestamp().toString())
                .value(sensor.getValue())
                .deviceName(sensor.getDevice().getName())
                .build();
    }
}
