package com.ironhack.sanctuary.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

public class Volunteer extends User {

    private String availability;
    private String skills;
    private boolean needsSupervision;

}
