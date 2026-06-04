package com.ironhack.sanctuary.model;

import com.ironhack.sanctuary.enums.HealthStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "animal")

public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String species;
    private LocalDate rescueDate;
    private String medicalHistory;

    @Enumerated(EnumType.STRING)
    private HealthStatus healthStatus;

    @OneToMany(mappedBy = "animal", fetch = FetchType.EAGER)
    private List<Task> tasks = new ArrayList<>();
}
