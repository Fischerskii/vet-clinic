package ru.trofimov.vetclinic.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.vetclinic.model.PetDTO;
import ru.trofimov.vetclinic.service.PetService;

@RestController
public class PetController {

    private static final Logger log = LoggerFactory.getLogger(PetController.class);
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping("/pets")
    public ResponseEntity<PetDTO> createPet(@RequestBody @Valid PetDTO petDto) {
        log.info("Create pet: {}", petDto);
        PetDTO createdPet = petService.createPet(petDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPet);
    }

    @GetMapping("/pets/{id}")
    public PetDTO findById(@PathVariable Long id) {
        log.info("Find pet by id: {}", id);
        return petService.findByPetId(id);
    }

    @PutMapping("/pets/{id}")
    public ResponseEntity<PetDTO> updatePet(@PathVariable Long id,
                                            @RequestBody @Valid PetDTO petDto) {
        log.info("Update pet: {}", petDto);
        PetDTO updatedPet = petService.updatePet(id, petDto);
        return ResponseEntity.ok(updatedPet);
    }

    @DeleteMapping("/pets/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        log.info("Delete pet: {}", id);
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
}
