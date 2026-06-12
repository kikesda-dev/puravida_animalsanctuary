package com.ironhack.sanctuary.service;

import com.ironhack.sanctuary.dto.DonationDTO;
import com.ironhack.sanctuary.model.Donation;
import com.ironhack.sanctuary.model.User;
import com.ironhack.sanctuary.repository.DonationRepository;
import com.ironhack.sanctuary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public Donation getDonationById(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found"));
    }

    public Donation saveDonation(DonationDTO dto) {
        Donation donation = new Donation();
        donation.setAmount(dto.getAmount());
        donation.setMessage(dto.getMessage());
        donation.setDonationDate(dto.getDonationDate());
        donation.setAnonymous(dto.getIsAnonymous());
        donation.setDonorName(dto.getDonorName());

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
            donation.setUser(user);
        }

        return donationRepository.save(donation);
    }

    public void deleteDonation(Long id) {
        donationRepository.deleteById(id);
    }
}