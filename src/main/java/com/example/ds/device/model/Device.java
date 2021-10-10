package com.example.ds.device.model;

import com.example.ds.client.model.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, nullable = false)
    private String name;

    @Column(length = 512)
    private String description;

    @Column(length = 100)
    private String location;

    @Column(name = "avg_consumption")
    private Integer averageConsumption;

    @Column(name = "max_consumption")
    private Integer maxConsumption;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}