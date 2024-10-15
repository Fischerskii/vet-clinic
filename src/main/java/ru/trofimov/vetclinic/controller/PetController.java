package ru.trofimov.vetclinic.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.vetclinic.dto.PetDTO;
import ru.trofimov.vetclinic.mapper.PetMapper;
import ru.trofimov.vetclinic.model.Pet;
import ru.trofimov.vetclinic.service.PetService;

@RestController
public class PetController {

    private static final Logger log = LoggerFactory.getLogger(PetController.class);
    private final PetService petService;
    private final PetMapper petMapper;

    @Autowired
    public PetController(PetService petService, PetMapper petMapper) {
        this.petService = petService;
        this.petMapper = petMapper;
    }

    @PostMapping("/pets")
    public ResponseEntity<PetDTO> createPet(@RequestBody @Valid PetDTO petDto) {
        log.info("Create pet: {}", petDto);
        Pet pet = petService.createPet(petMapper.toEntity(petDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(petMapper.toDTO(pet));
    }

    @GetMapping("/pets/{id}")
    public PetDTO findById(@PathVariable Long id) {
        log.info("Find pet by id: {}", id);
        Pet pet = petService.findByPetId(id);
        return petMapper.toDTO(pet);
    }

    @PutMapping("/pets/{id}")
    public ResponseEntity<PetDTO> updatePet(@PathVariable Long id,
                                            @RequestBody @Valid PetDTO petDto) {
        log.info("Update pet: {}", petDto);
        Pet updatedPet = petService.updatePet(id, petMapper.toEntity(petDto));
        return ResponseEntity.ok(petMapper.toDTO(updatedPet));
    }

    @DeleteMapping("/pets/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        log.info("Delete pet: {}", id);
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
}
