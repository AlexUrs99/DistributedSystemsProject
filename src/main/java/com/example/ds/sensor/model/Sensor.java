package com.example.ds.sensor.model;

import com.example.ds.device.model.Device;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, nullable = false)
    private String name;

    @Column(length = 32)
    private Date timestamp;

    private Integer value;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;
}
