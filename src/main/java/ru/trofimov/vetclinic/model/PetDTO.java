package ru.trofimov.vetclinic.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Long userId;

    public PetDTO() {
    }

    public PetDTO(Long id, String name, Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PetDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                '}';
    }
}
