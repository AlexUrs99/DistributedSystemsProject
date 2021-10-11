package com.example.ds.device.service;

import com.example.ds.client.exception.ClientNotFoundException;
import com.example.ds.client.model.Client;
import com.example.ds.client.repository.ClientRepository;
import com.example.ds.device.converter.DeviceConverter;
import com.example.ds.device.dto.DeviceDto;
import com.example.ds.device.exception.DeviceNotFoundException;
import com.example.ds.device.model.Device;
import com.example.ds.device.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final ClientRepository clientRepository;

    public List<DeviceDto> getAllDevices() {
        return deviceRepository.findAll().stream()
                .map(DeviceConverter::convertToDeviceDTO)
                .collect(Collectors.toList());
    }


    public DeviceDto getDeviceAtId(String id) {
        Device device = deviceRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new DeviceNotFoundException("Couldn't find device at id: " + id + "!"));
        return DeviceConverter.convertToDeviceDTO(device);
    }


    public DeviceDto saveDevice(DeviceDto body) {
        Client client = clientRepository.findByName(body.getClientName())
                .orElseThrow(() -> new ClientNotFoundException("Couldn't find client with name: " + body.getClientName() + "!"));

        Device builtDevice = Device.builder()
                .name(body.getName())
                .description(body.getDescription())
                .averageConsumption(body.getAverageConsumption())
                .maxConsumption(body.getMaxConsumption())
                .location(body.getLocation())
                .client(client)
                .build();

        return DeviceConverter.convertToDeviceDTO(
                deviceRepository.save(builtDevice));
    }

    public DeviceDto editDevice(String id, DeviceDto body) {
        Device foundDevice = deviceRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new DeviceNotFoundException("Couldn't find device at id: " + id + "!"));

        Device updatedDevice = updateDeviceWithNewBody(foundDevice, body);

        return DeviceConverter.convertToDeviceDTO(updatedDevice);
    }

    private Device updateDeviceWithNewBody(Device foundDevice, DeviceDto body) {
        Client client = clientRepository.findByName(body.getClientName())
                .orElseThrow(() -> new ClientNotFoundException("Couldn't find client with name: " + body.getClientName() + "!"));

        foundDevice.setClient(client);
        foundDevice.setDescription(body.getDescription());
        foundDevice.setAverageConsumption(body.getAverageConsumption());
        foundDevice.setMaxConsumption(body.getMaxConsumption());
        foundDevice.setLocation(body.getLocation());
        foundDevice.setName(body.getName());

        return deviceRepository.save(foundDevice);
    }

    public String deleteDevice(String id) {
        try {
            deviceRepository.deleteById(Long.parseLong(id));
            return "Deleted device at id: " + id + ".";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
