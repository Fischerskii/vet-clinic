package ru.trofimov.vetclinic.mapper;

import org.mapstruct.Mapper;
import ru.trofimov.vetclinic.dto.PetDTO;
import ru.trofimov.vetclinic.model.Pet;

@Mapper(componentModel = "spring")
public interface PetMapper {

    PetDTO toDTO(Pet pet);
    Pet toEntity(PetDTO petDTO);
}
