package com.ironhack.sanctuary.controller;

import com.ironhack.sanctuary.dto.DonationDTO;
import com.ironhack.sanctuary.model.Donation;
import com.ironhack.sanctuary.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;

    @GetMapping
    public List<Donation> getAllDonations() {
        return donationService.getAllDonations();
    }

    @GetMapping("/{id}")
    public Donation getDonationById(@PathVariable Long id) {
        return donationService.getDonationById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Donation createDonation(@RequestBody DonationDTO donationDTO) {
        return donationService.saveDonation(donationDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDonation(@PathVariable Long id) {
        donationService.deleteDonation(id);
    }
}