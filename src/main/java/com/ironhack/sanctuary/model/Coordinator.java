package com.ironhack.sanctuary.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

public class Coordinator extends User {
    private String managementArea;
    private String emergencyPhone;
}
