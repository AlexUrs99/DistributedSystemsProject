package com.example.ds.device.controller;

import com.example.ds.device.dto.DeviceDto;
import com.example.ds.device.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    public ResponseEntity<List<DeviceDto>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> getDeviceAtId(@PathVariable String id) {
        return ResponseEntity.ok(deviceService.getDeviceAtId(id));
    }

    @PostMapping
    public ResponseEntity<DeviceDto> saveDevice(@RequestBody DeviceDto body) {
        return ResponseEntity.ok(deviceService.saveDevice(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceDto> editDevice(@PathVariable String id, @RequestBody DeviceDto body) {
        return ResponseEntity.ok(deviceService.editDevice(id, body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable String id) {
        return ResponseEntity.ok(deviceService.deleteDevice(id));
    }
}
