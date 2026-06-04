package com.ironhack.sanctuary.controller;

import com.ironhack.sanctuary.model.Animal;
import com.ironhack.sanctuary.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping
    public List<Animal> getAllAnimals() {
        return animalService.getAllAnimals();
    }

    @GetMapping("/{id}")
    public Animal getAnimalById(@PathVariable Long id) {
        return animalService.getAnimalById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Animal updateAnimal(@PathVariable Long id, @RequestBody Animal animal) {
        return animalService.updateAnimal(id, animal);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Animal createAnimal(@RequestBody Animal animal) {
        return animalService.saveAnimal(animal);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
    }

}