package com.ironhack.sanctuary.service;

import com.ironhack.sanctuary.model.Animal;
import com.ironhack.sanctuary.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    public Animal getAnimalById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found"));
    }

    public Animal saveAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    public Animal updateAnimal(Long id, Animal animal){
        getAnimalById(id);
        animal.setId(id);
        return animalRepository.save(animal);
    }

    public void deleteAnimal(Long id) {
        animalRepository.deleteById(id);
    }


}