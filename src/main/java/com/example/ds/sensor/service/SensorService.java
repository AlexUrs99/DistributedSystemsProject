package com.example.ds.sensor.service;

import com.example.ds.device.exception.DeviceNotFoundException;
import com.example.ds.device.model.Device;
import com.example.ds.device.repository.DeviceRepository;
import com.example.ds.sensor.converter.SensorConverter;
import com.example.ds.sensor.dto.SensorDto;
import com.example.ds.sensor.exception.SensorNotFoundException;
import com.example.ds.sensor.model.Sensor;
import com.example.ds.sensor.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;
    private final DeviceRepository deviceRepository;

    public List<SensorDto> getAllSensors() {
        return sensorRepository.findAll().stream()
                .map(SensorConverter::convertToSensorDTO)
                .collect(Collectors.toList());
    }

    public SensorDto getSensorAtId(String id) {
        Sensor sensor = sensorRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new SensorNotFoundException("Couldn't find sensor at id: " + id + "!"));
        return SensorConverter.convertToSensorDTO(sensor);
    }


    public SensorDto saveSensor(SensorDto body) {
        Device device = deviceRepository.findByName(body.getDeviceName())
                .orElseThrow(() -> new DeviceNotFoundException("Couldn't find client with name: " + body.getDeviceName() + "!"));

        Sensor builtService = Sensor.builder()
                .name(body.getName())
                .timestamp(Date.valueOf(body.getTimestamp()))
                .device(device)
                .value(body.getValue())
                .build();

        return SensorConverter.convertToSensorDTO(
                sensorRepository.save(builtService));
    }

    public SensorDto editSensor(String id, SensorDto body) {
        Sensor foundSensor = sensorRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new SensorNotFoundException("Couldn't find sensor at id: " + id + "!"));

        Sensor updatedSensor = updateSensorWithNewBody(foundSensor, body);

        return SensorConverter.convertToSensorDTO(updatedSensor);
    }

    private Sensor updateSensorWithNewBody(Sensor foundSensor, SensorDto body) {
        Device device = deviceRepository.findByName(body.getDeviceName())
                .orElseThrow(() -> new DeviceNotFoundException("Couldn't find device with name: " + body.getName() + "!"));

        foundSensor.setDevice(device);
        foundSensor.setTimestamp(Date.valueOf(body.getTimestamp()));
        foundSensor.setName(body.getName());
        foundSensor.setValue(body.getValue());

        return sensorRepository.save(foundSensor);
    }

    public String deleteSensor(String id) {
        try {
            sensorRepository.deleteById(Long.parseLong(id));
            return "Deleted sensor at id: " + id + ".";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
