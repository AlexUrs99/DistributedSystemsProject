package com.example.ds.sensor.service;

import com.example.ds.device.DeviceFactory;
import com.example.ds.device.repository.DeviceRepository;
import com.example.ds.sensor.SensorFactory;
import com.example.ds.sensor.dto.SensorDto;
import com.example.ds.sensor.exception.SensorNotFoundException;
import com.example.ds.sensor.model.Sensor;
import com.example.ds.sensor.repository.SensorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.ServiceNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SensorServiceTest {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SensorService sensorService;

    @BeforeEach
    void setUp() {
        sensorRepository.deleteAll();
        deviceRepository.deleteAll();

        sensorRepository.saveAll(SensorFactory.generateListOfSensors(5));
        deviceRepository.saveAll(DeviceFactory.generateListOfDevices(5));
    }

    @Test
    void expectToReceiveTheListContainingAllTheSensors() {
        List<SensorDto> allSensors = sensorService.getAllSensors();

        assertEquals(allSensors.size(), sensorRepository.findAll().size());
    }

    @Test
    void expectToReceiveTheSensorAtTheSpecifiedID() {
        Sensor savedSensor = sensorRepository.save(SensorFactory.generateSensor());

        SensorDto sensorDTO = sensorService.getSensorAtId(savedSensor.getId().toString());

        assertEquals(savedSensor.getName(), sensorDTO.getName());
        assertEquals(savedSensor.getTimestamp().toString(), sensorDTO.getTimestamp());
        assertEquals(savedSensor.getValue(), sensorDTO.getValue());
        assertEquals("Not currently assigned to any device.", sensorDTO.getDeviceName());
    }

    @Test
    void whenSearchingForASensorAtAnIDThatIsNotInTheDatabase_ExpectASensorNotFoundException() {
        assertThatThrownBy(() -> sensorService.getSensorAtId("999"))
                .isInstanceOf(SensorNotFoundException.class)
                .hasMessage("Couldn't find sensor at id: 999!");
    }

    @Test
    void whenASensorIsSaved_ExpectItToBeAddedToTheDatabase() {
        SensorDto body = SensorFactory.generateSensorDto();
        SensorDto sensorDTO = sensorService.saveSensor(body);

        assertEquals(body.getDeviceName(), sensorDTO.getDeviceName());
        assertEquals(body.getValue(), sensorDTO.getValue());
        assertEquals(body.getName(), sensorDTO.getName());
        assertEquals(body.getTimestamp(), sensorDTO.getTimestamp());
    }

    @Test
    void whenEditingASensor_IfSensorIDIsNotGood_ExceptSensorNotFoundException() {
        SensorDto body = SensorFactory.generateSensorDto();
        assertThatThrownBy(() -> sensorService.editSensor("999", body))
                .isInstanceOf(SensorNotFoundException.class)
                .hasMessage("Couldn't find sensor at id: 999!");
    }

    @Test
    void whenDeletingADeviceSensor_IfSensorIDIsNotGood_ExceptSensorNotFoundException() {
        assertThatThrownBy(() -> sensorService.deleteSensor("999"))
                .isInstanceOf(SensorNotFoundException.class)
                .hasMessage("Couldn't find sensor at id: 999!");
    }

    @Test
    void whenProvidingASensorBodyAndAnId_ExpectTheEditToHappenSuccessfully() {
        Sensor sensor = SensorFactory.generateSensor();
        String savedDeviceID = sensorRepository.save(sensor).getId().toString();

        SensorDto body = SensorFactory.generateAnotherSensorDTO();

        SensorDto editedSensor = sensorService.editSensor(savedDeviceID, body);

        assertEquals(body, editedSensor);
    }

    @Test
    void whenSuccessfullyDeletingASensor_ExpectThisMessage() {
        String savedDeviceID = sensorRepository.save(SensorFactory.generateSensor()).getId().toString();

        assertThat(sensorService.deleteSensor(savedDeviceID)).isEqualTo("Successfully deleted sensor at id: " + savedDeviceID + ".");
    }
}