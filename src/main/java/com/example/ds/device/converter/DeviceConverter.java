package com.example.ds.device.converter;

import com.example.ds.device.dto.DeviceDto;
import com.example.ds.device.model.Device;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DeviceConverter {

    public DeviceDto convertDeviceToDeviceDTO(Device device) {
        return DeviceDto.builder()
                .name(device.getName())
                .clientName(device.getClient().getName())
                .averageConsumption(device.getAverageConsumption())
                .maxConsumption(device.getMaxConsumption())
                .location(device.getLocation())
                .build();
    }
}
