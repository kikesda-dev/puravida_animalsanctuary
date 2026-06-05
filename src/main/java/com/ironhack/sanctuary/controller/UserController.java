package com.ironhack.sanctuary.controller;

import com.ironhack.sanctuary.model.Coordinator;
import com.ironhack.sanctuary.model.User;
import com.ironhack.sanctuary.model.Volunteer;
import com.ironhack.sanctuary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/volunteer")
    @ResponseStatus(HttpStatus.CREATED)
    public User createVolunteer(@RequestBody Volunteer volunteer) {
        return userService.saveUser(volunteer);
    }

    @PostMapping("/coordinator")
    @ResponseStatus(HttpStatus.CREATED)
    public User createCoordinator(@RequestBody Coordinator coordinator) {
        return userService.saveUser(coordinator);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}