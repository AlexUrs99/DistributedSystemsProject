package com.example.ds.client.model;

import com.example.ds.role.model.Role;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String name;

    @Column(name = "birth_date", length = 32)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthdate;

    @Column(length = 128)
    private String address;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
