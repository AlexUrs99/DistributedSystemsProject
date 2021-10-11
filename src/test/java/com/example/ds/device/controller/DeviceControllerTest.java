package com.example.ds.device.controller;

import com.example.ds.device.DeviceFactory;
import com.example.ds.device.dto.DeviceDto;
import com.example.ds.device.model.Device;
import com.example.ds.device.service.DeviceService;
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

class DeviceControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Mock
    private DeviceService deviceService;

    @InjectMocks
    private DeviceController deviceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(deviceController).build();
    }

    @Test
    void whenGetAllDevicesIsCalled_ExpectTheListOfDeviceDTOs() throws Exception {
        List<DeviceDto> clients = DeviceFactory.generateListOfDeviceDTOs(10);

        when(deviceService.getAllDevices()).thenReturn(clients);

        mockMvc.perform(get("/devices")
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        verify(deviceService, times(1)).getAllDevices();

        assertThat(10).isEqualTo(clients.size());
    }

    @Test
    void whenSearchingForAClientAtSpecificID_ExpectThatSpecificClient() throws Exception {
        Device device = DeviceFactory.generateDevice();
        DeviceDto deviceDTO = DeviceFactory.generateDeviceDTO();
        String deviceID = device.getId().toString();

        when(deviceService.getDeviceAtId(deviceID)).thenReturn(deviceDTO);

        mockMvc.perform(get("/devices" + "/" + deviceID)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value("RandomDevice"))
                .andExpect(jsonPath("$.location").value("RandomLocation"))
                .andExpect(jsonPath("$.maxConsumption").value(10))
                .andExpect(jsonPath("$.averageConsumption").value("5"));

        verify(deviceService, times(1)).getDeviceAtId(device.getId().toString());
    }

    @Test
    void whenSavingANewClient_ShouldReturnItsCorrespondingDTO() throws Exception {
        DeviceDto deviceDTO = DeviceFactory.generateDeviceDTO();

        when(deviceService.saveDevice(deviceDTO)).thenReturn(deviceDTO);

        mockMvc.perform(post("/devices")
                        .content(objectMapper.writeValueAsString(deviceDTO))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("RandomDevice"))
                .andExpect(jsonPath("$.location").value("RandomLocation"))
                .andExpect(jsonPath("$.maxConsumption").value(10))
                .andExpect(jsonPath("$.averageConsumption").value("5"));

        verify(deviceService, times(1)).saveDevice(deviceDTO);
    }

    @Test
    void whenEditingAUser_ExpectTheUserToReceiveTheUpdate() throws Exception {
        Device device = DeviceFactory.generateDevice();
        DeviceDto deviceDTO = DeviceFactory.generateDeviceDTO();
        String deviceID = device.getId().toString();

        when(deviceService.editDevice(deviceID, deviceDTO)).thenReturn(deviceDTO);

        mockMvc.perform(put("/devices" + "/" + deviceID)
                        .content(objectMapper.writeValueAsString(deviceDTO))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("RandomDevice"))
                .andExpect(jsonPath("$.location").value("RandomLocation"))
                .andExpect(jsonPath("$.maxConsumption").value(10))
                .andExpect(jsonPath("$.averageConsumption").value("5"));

        verify(deviceService, times(1)).editDevice(deviceID, deviceDTO);
    }

    @Test
    void whenDeletingClientWithValidID_ShouldReturnOkStatus() throws Exception {
        Device device = DeviceFactory.generateDevice();
        String deviceID = device.getId().toString();

        when(deviceService.deleteDevice(deviceID)).thenReturn("Deleted device at id: " + deviceID + ".");

        mockMvc.perform(delete("/devices" + "/" + deviceID))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted device at id: " + deviceID + "."));

        verify(deviceService, times(1)).deleteDevice(deviceID);
    }
}