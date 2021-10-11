package com.example.ds.sensor.controller;

import com.example.ds.sensor.dto.SensorDto;
import com.example.ds.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @GetMapping
    public ResponseEntity<List<SensorDto>> getAllSensors() {
        return ResponseEntity.ok(sensorService.getAllSensors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorDto> getSensorAtId(@PathVariable String id) {
        return ResponseEntity.ok(sensorService.getSensorAtId(id));
    }

    @PostMapping
    public ResponseEntity<SensorDto> saveSensor(@RequestBody SensorDto body) {
        return ResponseEntity.ok(sensorService.saveSensor(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensorDto> editSensor(@PathVariable String id, @RequestBody SensorDto body) {
        return ResponseEntity.ok(sensorService.editSensor(id, body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSensor(@PathVariable String id) {
        return ResponseEntity.ok(sensorService.deleteSensor(id));
    }
}
