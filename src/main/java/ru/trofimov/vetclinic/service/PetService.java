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

    private final UserService userService;

    @Autowired
    public PetService(UserService userService) {
        this.idCounter = 0L;
        this.pets = new HashMap<>();
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
        PetDTO createdPet = pets.put(newId, newPet);
        userService.addPet(userId, createdPet);
        return newPet;
    }

    public PetDTO findByPetId(Long id) {
        return Optional.ofNullable(pets.get(id))
                .orElseThrow(() -> new NoSuchElementException(
                        "Pet with id %s not found".formatted(id)));
    }

    public PetDTO updatePet(Long id, PetDTO petDto) {
        findByPetId(id);

        PetDTO updatedPet = new PetDTO(
                id,
                petDto.getName(),
                petDto.getUserId()
        );
        pets.put(id, updatedPet);
        return updatedPet;
    }

    public void deletePet(Long id) {
        PetDTO removedPet = pets.remove(id);

        if (removedPet == null) {
            throw new NoSuchElementException("Pet with id %s not found".formatted(id));
        }
    }
}
