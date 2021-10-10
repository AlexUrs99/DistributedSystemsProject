package com.example.ds.sensor.service;

import com.example.ds.sensor.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;
}
