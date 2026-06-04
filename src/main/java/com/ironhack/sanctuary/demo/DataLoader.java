package com.ironhack.sanctuary.demo;

import com.ironhack.sanctuary.model.Coordinator;
import com.ironhack.sanctuary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if(userRepository.count() == 0) {
            Coordinator admin = new Coordinator();
            admin.setEmail("admin@puravidasanctuary.com");
            admin.setPassword(passwordEncoder.encode("1234"));
            admin.setManagementArea("Direccion General");
            admin.setEmergencyPhone("601234567");

            userRepository.save(admin);
        }
    }
}
