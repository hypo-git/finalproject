package com.finalproject.finalproject.data.mapper;

import com.finalproject.finalproject.data.dto.EntityDefinitionDTOs.EntityDefinitionCreateDTO;
import com.finalproject.finalproject.data.dto.EntityDefinitionDTOs.EntityDefinitionUpdateDTO;
import com.finalproject.finalproject.data.dto.EntityDefinitionDTOs.EntityDefinitionResponseDTO;
import com.finalproject.finalproject.data.model.EntityDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntityDefinitionMapper {

    // DTO → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    EntityDefinition toEntity(EntityDefinitionCreateDTO dto);
    // RequestDTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    EntityDefinition toEntity(EntityDefinitionUpdateDTO requestDTO);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget EntityDefinition entity, EntityDefinitionUpdateDTO dto);
    //Entity -> DTO
    EntityDefinitionResponseDTO toResponseDTO(EntityDefinition entity);
    //listings
    List<EntityDefinitionResponseDTO> toResponseDTO(List<EntityDefinition> entity);


}
