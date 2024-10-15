package ru.trofimov.vetclinic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long id;

    @NotBlank(message = "Name cannot be null")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Min(value = 0, message = "Age cannot be less than 0")
    @NotNull
    private Integer age;
    private List<PetDTO> pets;

    public UserDTO() {}

    public UserDTO(Long id, String name, String email, Integer age, List<PetDTO> pets) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.pets = pets;
    }

    public @Null Long getId() {
        return id;
    }

    public @NotBlank String getName() {
        return name;
    }

    public @Email String getEmail() {
        return email;
    }

    public @NotNull Integer getAge() {
        return age;
    }

    public List<PetDTO> getPets() {
        return pets;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", pets=" + pets +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public void setAge(@NotNull Integer age) {
        this.age = age;
    }

    public void setPets(List<PetDTO> pets) {
        this.pets = pets;
    }
}
