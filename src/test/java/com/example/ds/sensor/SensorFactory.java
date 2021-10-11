package com.example.ds.sensor;

import com.example.ds.sensor.dto.SensorDto;
import com.example.ds.sensor.model.Sensor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SensorFactory {

    public static Sensor generateSensor() {
        return Sensor.builder()
                .id(1L)
                .name("RandomSensor")
                .timestamp(Date.valueOf("1999-10-28"))
                .value(10)
                .build();
    }

    public static SensorDto generateSensorDto() {
        return SensorDto.builder()
                .name("RandomSensor")
                .timestamp(Date.valueOf("1999-10-28").toString())
                .deviceName("Device 0")
                .value(10)
                .build();
    }

    public static SensorDto generateAnotherSensorDTO() {
        return SensorDto.builder()
                .name("AnotherSensor")
                .timestamp(Date.valueOf("2000-05-10").toString())
                .deviceName("Device 1")
                .value(5)
                .build();
    }

    public static List<Sensor> generateListOfSensors(int numberOfSensors) {
        List<Sensor> sensors = new ArrayList<>();

        for (int i = 0; i < numberOfSensors; i++) {
            sensors.add(
                    Sensor.builder()
                            .name("Sensor " + i)
                            .timestamp(Date.valueOf("1999-10-28"))
                            .build()
            );
        }
        return sensors;
    }

    public static List<SensorDto> generateListOfSensorDTOs(int numberOfSensors) {
        List<SensorDto> sensors = new ArrayList<>();

        for (int i = 0; i < numberOfSensors; i++) {
            sensors.add(
                    SensorDto.builder()
                            .name("Sensor " + i)
                            .deviceName("Device 0")
                            .build()
            );
        }
        return sensors;
    }
}
