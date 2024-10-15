package ru.trofimov.vetclinic.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.vetclinic.dto.UserDTO;
import ru.trofimov.vetclinic.mapper.UserMapper;
import ru.trofimov.vetclinic.model.User;
import ru.trofimov.vetclinic.service.UserService;

import java.util.List;

@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDto) {
        log.info("Post request for create user {}", userDto);
        User createdUser = userService.createUser(userMapper.toEntity(userDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.toDTO(createdUser));
    }

    @GetMapping("/users/{id}")
    public UserDTO findById(@PathVariable Long id) {
        log.info("Get request for find user with id={}", id);
        User user = userService.findUserById(id);
        return userMapper.toDTO(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,
                                              @RequestBody @Valid UserDTO userDto) {
        log.info("Put request for update user with id={}", id);
        User updatedUser = userService.updateUser(id, userMapper.toEntity(userDto));
        return ResponseEntity.ok(userMapper.toDTO(updatedUser));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Delete request for delete user with id={}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users")
    public List<UserDTO> findAllUsers() {
        List<User> allUsers = userService.findAllUsers();
        log.info("all users: {}", allUsers);

        List<UserDTO> list = allUsers.stream()
                .map(userMapper::toDTO)
                .toList();
        log.info("dto users list: {}", list);
        log.info("Get request for find all users");
        return userService.findAllUsers().stream()
                .map(userMapper::toDTO)
                .toList();
    }
}
