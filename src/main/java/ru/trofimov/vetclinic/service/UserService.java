package ru.trofimov.vetclinic.service;

import org.springframework.stereotype.Service;
import ru.trofimov.vetclinic.model.Pet;
import ru.trofimov.vetclinic.model.User;

import java.util.*;

@Service
public class UserService {

    private Long idCounter;
    private final Map<Long, User> users;

    public UserService() {
        this.idCounter = 0L;
        this.users = new HashMap<>();
    }

    public User createUser(User user) {
        Long newId = ++idCounter;
        User newUser = new User(
                newId,
                user.getName(),
                user.getEmail(),
                user.getAge(),
                new ArrayList<>()
                );

        users.put(newId, newUser);
        return newUser;
    }

    public User findUserById(Long id) {
        return Optional.ofNullable(users.get(id))
                .orElseThrow(() -> new NoSuchElementException(
                        "User with id %s not found".formatted(id)));
    }

    public User updateUser(Long id, User user) {
        User oldUser = findUserById(id);

        User updatedUser = new User(
                id,
                user.getName(),
                user.getEmail(),
                user.getAge(),
                oldUser.getPets()
        );
        users.put(id, updatedUser);
        return updatedUser;
    }

    public void deleteUser(Long id) {
        User removedUser = users.remove(id);
        if (removedUser == null) {
            throw new NoSuchElementException("User with id %s not found".formatted(id));
        }
    }

    public List<User> findAllUsers() {
        return new ArrayList<>(users.values());
    }

    public void addPet(Long id, Pet pet) {
        User user = users.get(id);
        if (user == null) {
            throw new NoSuchElementException("User with id %s not found".formatted(id));
        }
        List<Pet> pets = user.getPets();
        pets.add(pet);
        user.setPets(pets);
        users.put(id, user);
    }

    public void updatePet(Long petId, Pet pet) {
        User user = users.get(petId);
        if (user == null) {
            throw new NoSuchElementException("User with petId %s not found".formatted(petId));
        }
        List<Pet> pets = user.getPets();
        List<Pet> updatedPetsList = pets.stream()
                .map(currentPet -> currentPet.getId().equals(petId) ? pet : currentPet)
                .toList();

        user.setPets(updatedPetsList);
        users.put(petId, user);
    }

    public void deletePet(Long userId, Long petId) {
        User user = users.get(userId);
        if (user == null) {
            throw new NoSuchElementException("User with id %s not found".formatted(petId));
        }

        List<Pet> pets = user.getPets();
        pets.removeIf(pet -> pet.getId().equals(petId));
    }
}
