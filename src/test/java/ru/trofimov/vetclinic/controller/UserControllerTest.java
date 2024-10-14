package ru.trofimov.vetclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.trofimov.vetclinic.model.UserDTO;
import ru.trofimov.vetclinic.service.UserService;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldSuccessCreateUser() throws Exception {
        UserDTO user = new UserDTO(
                null,
                "sid",
                "sid@mail.com",
                26,
                new ArrayList<>()
        );

        String userJson = objectMapper.writeValueAsString(user);

        String createBookJson = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDTO userResponse = objectMapper.readValue(createBookJson, UserDTO.class);

        Assertions.assertNotNull(userResponse.getId());
        Assertions.assertEquals(user.getName(), userResponse.getName());
    }

    @Test
    void shouldNotCreateUserRequestNotValid() throws Exception {
        UserDTO user = new UserDTO(
                null,
                "sid",
                "sid@mail.com",
                null,
                new ArrayList<>()
        );

        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldSuccessSearchUser() throws Exception {
        UserDTO user = new UserDTO(
                null,
                "sid",
                "sid@mail.com",
                22,
                new ArrayList<>()
        );

        user = userService.createUser(user);

        String foundUserJson = mockMvc.perform(get("/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDTO foundUser = objectMapper.readValue(foundUserJson, UserDTO.class);

        org.assertj.core.api.Assertions.assertThat(user)
                .usingRecursiveComparison()
                .isEqualTo(foundUser);
    }

    @Test
    void shouldReturnNotFoundWhenUserNotPresent() throws Exception {
        mockMvc.perform(get("/users/{id}", Integer.MAX_VALUE))
                .andExpect(status().isNotFound());
    }
}
