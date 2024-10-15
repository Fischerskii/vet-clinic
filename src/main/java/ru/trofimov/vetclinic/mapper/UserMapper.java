package ru.trofimov.vetclinic.mapper;

import org.mapstruct.Mapper;
import ru.trofimov.vetclinic.dto.UserDTO;
import ru.trofimov.vetclinic.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO userDTO);

    UserDTO toDTO(User user);
}
