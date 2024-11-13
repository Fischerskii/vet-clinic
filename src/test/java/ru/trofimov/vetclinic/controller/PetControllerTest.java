package ru.trofimov.vetclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.trofimov.vetclinic.dto.PetDTO;
import ru.trofimov.vetclinic.dto.UserDTO;
import ru.trofimov.vetclinic.mapper.PetMapper;
import ru.trofimov.vetclinic.model.Pet;
import ru.trofimov.vetclinic.model.User;
import ru.trofimov.vetclinic.service.PetService;
import ru.trofimov.vetclinic.service.UserService;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private PetService petService;
    @Autowired
    private PetMapper petMapper;

    @Test
    void shouldSuccessCreatePet() throws Exception {
        User user = new User(
                null,
                "sid",
                "sid@mail.com",
                23,
                new ArrayList<>()
        );

        User createdUser = userService.createUser(user);

        PetDTO pet = new PetDTO(
                null,
                "charli",
                createdUser.getId()
        );

        String petJson = objectMapper.writeValueAsString(pet);

        String createPetJson = mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson)
                )
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString();

        PetDTO petResponse = objectMapper.readValue(createPetJson, PetDTO.class);

        Assertions.assertNotNull(petMapper.toEntity(petResponse).getUserId());
        Assertions.assertEquals(pet.getName(), petResponse.getName());
    }

    @Test
    void shouldNotCreatePetRequestNovValid() throws Exception {
        PetDTO pet = new PetDTO(
                null,
                "charli",
                null
        );

        String petJson = objectMapper.writeValueAsString(pet);

        mockMvc.perform(post("/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(petJson)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldSuccessDeletePet() throws Exception {
        User user = new User(
                null,
                "sid",
                "sid@mail.com",
                23,
                new ArrayList<>()
        );

        User createdUser = userService.createUser(user);

        Pet pet = new Pet(
                null,
                "charli",
                createdUser.getId()
        );

        Pet createdPet = petService.createPet(pet);

        Assertions.assertNotNull(petService.findByPetId(createdPet.getId()));

        mockMvc.perform(delete("/pets/{id}", createdPet.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(204))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThrows(NoSuchElementException.class,
                () -> petService.findByPetId(createdPet.getId()));
    }
}