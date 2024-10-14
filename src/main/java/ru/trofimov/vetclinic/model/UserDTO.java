package ru.trofimov.vetclinic.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @Null
    private Long id;

    @NotBlank
    private String name;

    @Email
    private String email;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<PetDTO> getPets() {
        return pets;
    }

    public void setPets(List<PetDTO> pets) {
        this.pets = pets;
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
}
