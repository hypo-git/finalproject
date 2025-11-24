package com.finalproject.finalproject.data.mapper;

import com.finalproject.finalproject.data.dto.EntityDefinitionDTO.EntityDefinitionCreateDTO;
import com.finalproject.finalproject.data.dto.EntityDefinitionDTO.EntityDefinitionResponseDTO;
import com.finalproject.finalproject.data.model.EntityDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EntityDefinitionMapper {

    EntityDefinitionMapper INSTANCE = Mappers.getMapper(EntityDefinitionMapper.class);

    // DTO → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    EntityDefinition toEntity(EntityDefinitionCreateDTO dto);

    //Entity -> DTO
    EntityDefinitionResponseDTO toResponseDTO(EntityDefinition entity);
}
