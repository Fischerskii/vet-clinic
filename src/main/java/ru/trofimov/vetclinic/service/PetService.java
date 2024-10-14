package ru.trofimov.vetclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trofimov.vetclinic.model.PetDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PetService {

    private Long idCounter;
    private final Map<Long, PetDTO> pets;

    private UserService userService;

    public PetService() {
        this.idCounter = 0L;
        this.pets = new HashMap<>();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public PetDTO createPet(PetDTO petDto) {
        Long newId = ++idCounter;
        Long userId = petDto.getUserId();

        PetDTO newPet = new PetDTO(
                newId,
                petDto.getName(),
                userId
        );
        pets.put(newId, newPet);
        userService.addPet(userId, newPet);
        return newPet;
    }

    public PetDTO findByPetId(Long id) {
        return Optional.ofNullable(pets.get(id))
                .orElseThrow(() -> new NoSuchElementException(
                        "Pet with id %s not found".formatted(id)));
    }

    public PetDTO updatePet(Long id, PetDTO petDto) {
        findByPetId(id);

        PetDTO pet = new PetDTO(
                id,
                petDto.getName(),
                petDto.getUserId()
        );
        pets.put(id, pet);
        userService.updatePet(id, pet);
        return pet;
    }

    public void deletePet(Long id) {
        PetDTO removedPet = pets.remove(id);

        if (removedPet == null) {
            throw new NoSuchElementException("Pet with id %s not found".formatted(id));
        }

        userService.deletePet(removedPet.getUserId(), id);
    }
}
