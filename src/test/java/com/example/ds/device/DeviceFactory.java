package com.example.ds.device;

import com.example.ds.device.dto.DeviceDto;
import com.example.ds.device.model.Device;

import java.util.ArrayList;
import java.util.List;

public class DeviceFactory {

    public static Device generateDevice() {
        return Device.builder()
                .id(1L)
                .name("RandomDevice")
                .location("RandomLocation")
                .maxConsumption(10)
                .averageConsumption(5)
                .build();
    }

    public static DeviceDto generateDeviceDTO() {
        return DeviceDto.builder()
                .name("RandomDevice")
                .clientName("Client 0")
                .location("RandomLocation")
                .maxConsumption(10)
                .averageConsumption(5)
                .build();
    }

    public static DeviceDto generateAnotherDeviceDTO() {
        return DeviceDto.builder()
                .name("AnotherDevice")
                .clientName("Client 1")
                .location("AnotherLocation")
                .maxConsumption(25)
                .averageConsumption(10)
                .build();
    }

    public static List<Device> generateListOfDevices(int numberOfDevices) {
        List<Device> devices = new ArrayList<>();

        for (int i = 0; i < numberOfDevices; i++) {
            devices.add(
                    Device.builder()
                            .name("Device " + i)
                            .build()
            );
        }
        return devices;
    }

    public static List<DeviceDto> generateListOfDeviceDTOs(int numberOfDevices) {
        List<DeviceDto> devices = new ArrayList<>();

        for (int i = 0; i < numberOfDevices; i++) {
            devices.add(
                    DeviceDto.builder()
                            .name("Device " + i)
                            .clientName("Client 0")
                            .build()
            );
        }
        return devices;
    }
}

