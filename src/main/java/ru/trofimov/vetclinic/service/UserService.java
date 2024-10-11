package ru.trofimov.vetclinic.service;

import org.springframework.stereotype.Service;
import ru.trofimov.vetclinic.model.PetDTO;
import ru.trofimov.vetclinic.model.UserDTO;

import java.util.*;

@Service
public class UserService {

    private Long idCounter;
    private final Map<Long, UserDTO> users;

    public UserService() {
        this.idCounter = 0L;
        this.users = new HashMap<>();
    }

    public UserDTO createUser(UserDTO user) {
        Long newId = ++idCounter;
        UserDTO newUser = new UserDTO(
                newId,
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getPets()
                );

        users.put(newId, newUser);
        return newUser;
    }

    public UserDTO findUserById(Long id) {
        return Optional.ofNullable(users.get(id))
                .orElseThrow(() -> new NoSuchElementException(
                        "User with id %s not found".formatted(id)));
    }

    public UserDTO updateUser(Long id, UserDTO user) {
        findUserById(id);

        UserDTO updatedUser = new UserDTO(
                id,
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getPets()
        );
        users.put(id, updatedUser);
        return updatedUser;
    }

    public void deleteUser(Long id) {
        UserDTO removedUser = users.remove(id);
        if (removedUser == null) {
            throw new NoSuchElementException("User with id %s not found".formatted(id));
        }
    }

    public void addPet(Long id, PetDTO pet) {
        UserDTO userDTO = users.get(id);
        if (userDTO == null) {
            throw new NoSuchElementException("User with id %s not found".formatted(id));
        }
        List<PetDTO> pets = userDTO.getPets();
        pets.add(pet);
        userDTO.setPets(pets);
        users.put(id, userDTO);
    }
}
