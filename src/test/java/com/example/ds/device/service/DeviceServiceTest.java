package com.example.ds.device.service;

import com.example.ds.client.ClientFactory;
import com.example.ds.client.repository.ClientRepository;
import com.example.ds.device.DeviceFactory;
import com.example.ds.device.dto.DeviceDto;
import com.example.ds.device.exception.DeviceNotFoundException;
import com.example.ds.device.model.Device;
import com.example.ds.device.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class DeviceServiceTest {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DeviceService deviceService;

    @BeforeEach
    void setUp() {
        deviceRepository.deleteAll();
        clientRepository.deleteAll();

        deviceRepository.saveAll(DeviceFactory.generateListOfDevices(5));
        clientRepository.saveAll(ClientFactory.generateListOfClients(5));
    }

    @Test
    void expectToReceiveTheListContainingAllTheDevices() {
        List<DeviceDto> allDevices = deviceService.getAllDevices();

        assertEquals(allDevices.size(), deviceRepository.findAll().size());
    }

    @Test
    void expectToReceiveTheDeviceAtTheSpecifiedID() {
        Device savedDevice = deviceRepository.save(DeviceFactory.generateDevice());

        DeviceDto deviceDto = deviceService.getDeviceAtId(savedDevice.getId().toString());

        assertEquals(savedDevice.getMaxConsumption(), deviceDto.getMaxConsumption());
        assertEquals(savedDevice.getDescription(), deviceDto.getDescription());
        assertEquals(savedDevice.getLocation(), deviceDto.getLocation());
        assertEquals(savedDevice.getAverageConsumption(), deviceDto.getAverageConsumption());
        assertEquals("Not currently assigned to any client.", deviceDto.getClientName());
        assertEquals(savedDevice.getName(), deviceDto.getName());
    }

    @Test
    void whenSearchingForADeviceAtAnIDThatIsNotInTheDatabase_ExpectADeviceNotFoundException() {
        assertThatThrownBy(() -> deviceService.getDeviceAtId("999"))
                .isInstanceOf(DeviceNotFoundException.class)
                .hasMessage("Couldn't find device at id: 999!");
    }

    @Test
    void whenADeviceIsSaved_ExpectItToBeAddedToTheDatabase() {
        DeviceDto body = DeviceFactory.generateDeviceDTO();
        DeviceDto deviceDto = deviceService.saveDevice(body);

        assertEquals(body.getMaxConsumption(), deviceDto.getMaxConsumption());
        assertEquals(body.getDescription(), deviceDto.getDescription());
        assertEquals(body.getLocation(), deviceDto.getLocation());
        assertEquals(body.getAverageConsumption(), deviceDto.getAverageConsumption());
        assertEquals(body.getName(), deviceDto.getName());
    }

    @Test
    void whenEditingADevice_IfDeviceIDIsNotGood_ExceptDeviceNotFoundException() {
        DeviceDto body = DeviceFactory.generateDeviceDTO();
        assertThatThrownBy(() -> deviceService.editDevice("999", body))
                .isInstanceOf(DeviceNotFoundException.class)
                .hasMessage("Couldn't find device at id: 999!");
    }

    @Test
    void whenDeletingADevice_IfDeviceIDIsNotGood_ExceptDeviceNotFoundException() {
        assertThatThrownBy(() -> deviceService.deleteDevice("999"))
                .isInstanceOf(DeviceNotFoundException.class)
                .hasMessage("Couldn't find device at id: 999!");
    }

    @Test
    void whenProvidingADeviceBodyAndAnId_ExpectTheEditToHappenSuccessfully() {
        Device device = DeviceFactory.generateDevice();
        String savedDeviceID = deviceRepository.save(device).getId().toString();

        DeviceDto body = DeviceFactory.generateAnotherDeviceDTO();

        DeviceDto editedDevice = deviceService.editDevice(savedDeviceID, body);

        assertEquals(body, editedDevice);
    }

    @Test
    void whenSuccessfullyDeletingADevice_ExpectThisMessage() {
        String savedDeviceID = deviceRepository.save(DeviceFactory.generateDevice()).getId().toString();

        assertThat(deviceService.deleteDevice(savedDeviceID)).isEqualTo("Successfully deleted device at id: " + savedDeviceID + ".");
    }
}