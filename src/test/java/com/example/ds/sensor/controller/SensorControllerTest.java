package com.example.ds.sensor.controller;

import com.example.ds.sensor.SensorFactory;
import com.example.ds.sensor.dto.SensorDto;
import com.example.ds.sensor.model.Sensor;
import com.example.ds.sensor.service.SensorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SensorControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Mock
    private SensorService sensorService;

    @InjectMocks
    private SensorController serviceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(serviceController).build();
    }

    @Test
    void whenGetAllSensorsIsCalled_ExpectTheListOfSensorDTOs() throws Exception {
        List<SensorDto> sensors = SensorFactory.generateListOfSensorDTOs(10);

        when(sensorService.getAllSensors()).thenReturn(sensors);

        mockMvc.perform(get("/sensors")
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        verify(sensorService, times(1)).getAllSensors();

        assertThat(10).isEqualTo(sensors.size());
    }

    @Test
    void whenSearchingForASensorAtSpecificID_ExpectThatSpecificSensor() throws Exception {
        Sensor sensor = SensorFactory.generateSensor();
        SensorDto sensorDTO = SensorFactory.generateSensorDto();
        String deviceID = sensor.getId().toString();

        when(sensorService.getSensorAtId(deviceID)).thenReturn(sensorDTO);

        mockMvc.perform(get("/sensors" + "/" + deviceID)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value("RandomSensor"))
                .andExpect(jsonPath("$.timestamp").value("1999-10-28"))
                .andExpect(jsonPath("$.value").value(10));

        verify(sensorService, times(1)).getSensorAtId(sensor.getId().toString());
    }

    @Test
    void whenSavingANewSensor_ShouldReturnItsCorrespondingDTO() throws Exception {
        SensorDto sensorDTO = SensorFactory.generateSensorDto();

        when(sensorService.saveSensor(sensorDTO)).thenReturn(sensorDTO);

        mockMvc.perform(post("/sensors")
                        .content(objectMapper.writeValueAsString(sensorDTO))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("RandomSensor"))
                .andExpect(jsonPath("$.timestamp").value("1999-10-28"))
                .andExpect(jsonPath("$.value").value(10));

        verify(sensorService, times(1)).saveSensor(sensorDTO);
    }

    @Test
    void whenEditingASensor_ExpectTheSensorToReceiveTheUpdate() throws Exception {
        Sensor sensor = SensorFactory.generateSensor();
        SensorDto sensorDTO = SensorFactory.generateSensorDto();
        String sensorID = sensor.getId().toString();

        when(sensorService.editSensor(sensorID, sensorDTO)).thenReturn(sensorDTO);

        mockMvc.perform(put("/sensors" + "/" + sensorID)
                        .content(objectMapper.writeValueAsString(sensorDTO))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("RandomSensor"))
                .andExpect(jsonPath("$.timestamp").value("1999-10-28"))
                .andExpect(jsonPath("$.value").value(10));

        verify(sensorService, times(1)).editSensor(sensorID, sensorDTO);
    }

    @Test
    void whenDeletingSensorWithValidID_ShouldReturnOkStatus() throws Exception {
        Sensor sensor = SensorFactory.generateSensor();
        String sensorID = sensor.getId().toString();

        when(sensorService.deleteSensor(sensorID)).thenReturn("Deleted sensor at id: " + sensorID + ".");

        mockMvc.perform(delete("/sensors" + "/" + sensorID))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted sensor at id: " + sensorID + "."));

        verify(sensorService, times(1)).deleteSensor(sensorID);
    }
}