package com.ironhack.sanctuary.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DonationDTO {
    private Double amount;
    private String message;
    private LocalDate donationDate;
    private Boolean isAnonymous;
    private String donorName;
    private Long userId;
}