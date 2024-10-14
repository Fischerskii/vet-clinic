package ru.trofimov.vetclinic.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.vetclinic.model.UserDTO;
import ru.trofimov.vetclinic.service.UserService;

import java.util.List;

@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDto) {
        log.info("Post request for create user {}", userDto);
        UserDTO createdUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/users/{id}")
    public UserDTO findById(@PathVariable Long id) {
        log.info("Get request for find user with id={}", id);
        return userService.findUserById(id);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,
                                              @RequestBody @Valid UserDTO userDto) {
        log.info("Put request for update user with id={}", id);
        UserDTO updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Delete request for delete user with id={}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users")
    public List<UserDTO> findAllUsers() {
        log.info("Get request for find all users");
        return userService.findAllUsers();
    }
}
