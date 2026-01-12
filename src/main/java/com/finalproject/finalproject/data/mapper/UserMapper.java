package com.finalproject.finalproject.data.mapper;

import com.finalproject.finalproject.data.dto.AuthResponse;
import com.finalproject.finalproject.data.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // User -> AuthResponse
    /*
    * EntityDefinitionResponseDTO toResponseDTO(EntityDefinition entity);
    * List<EntityDefinitionResponseDTO> toResponseDTO(List<EntityDefinition> entity);
    * */
    AuthResponse toAuthResponse(User user);
    List<AuthResponse> toAuthResponseList(List<User> user);
}
