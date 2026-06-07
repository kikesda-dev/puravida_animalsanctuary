package com.ironhack.sanctuary.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
@ToString(exclude = "user")
@NoArgsConstructor
@Table(name = "donations")

public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double amount;
    private String message;
    private LocalDate donationDate;
    private boolean isAnonymous;
    private String donorName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
