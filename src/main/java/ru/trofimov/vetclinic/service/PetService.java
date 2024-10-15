package ru.trofimov.vetclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trofimov.vetclinic.model.Pet;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PetService {

    private Long idCounter;
    private final Map<Long, Pet> pets;

    private UserService userService;

    public PetService() {
        this.idCounter = 0L;
        this.pets = new HashMap<>();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Pet createPet(Pet pet) {
        Long newId = ++idCounter;
        Long userId = pet.getUserId();

        Pet newPet = new Pet(
                newId,
                pet.getName(),
                userId
        );
        pets.put(newId, newPet);
        userService.addPet(userId, newPet);
        return newPet;
    }

    public Pet findByPetId(Long id) {
        return Optional.ofNullable(pets.get(id))
                .orElseThrow(() -> new NoSuchElementException(
                        "Pet with id %s not found".formatted(id)));
    }

    public Pet updatePet(Long id, Pet petDto) {
        findByPetId(id);

        Pet pet = new Pet(
                id,
                petDto.getName(),
                petDto.getUserId()
        );
        pets.put(id, pet);
        userService.updatePet(id, pet);
        return pet;
    }

    public void deletePet(Long id) {
        Pet removedPet = pets.remove(id);

        if (removedPet == null) {
            throw new NoSuchElementException("Pet with id %s not found".formatted(id));
        }

        userService.deletePet(removedPet.getUserId(), id);
    }
}
