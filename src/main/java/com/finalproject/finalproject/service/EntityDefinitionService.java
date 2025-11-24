package com.finalproject.finalproject.service;

import com.finalproject.finalproject.data.dto.EntityDefinitionDTO.EntityDefinitionCreateDTO;
import com.finalproject.finalproject.data.dto.EntityDefinitionDTO.EntityDefinitionResponseDTO;
import com.finalproject.finalproject.data.mapper.EntityDefinitionMapper;
import com.finalproject.finalproject.data.model.EntityDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.finalproject.finalproject.repository.EntityDefinitionRepository;

@Service
@RequiredArgsConstructor
public class EntityDefinitionService {

    private final EntityDefinitionRepository repository;
    private final EntityDefinitionMapper mapper;

    public EntityDefinitionResponseDTO createEntityDefinition(EntityDefinitionCreateDTO dto){
        if (repository.existsByEntityCode(dto.getEntityCode())){
            throw new RuntimeException("There is an entity with this code: " + dto.getEntityCode());
        }

        EntityDefinition entityDefinition = mapper.toEntity(dto);
        EntityDefinition saved = repository.save(entityDefinition);
        return mapper.toResponseDTO(saved);
    }

    public EntityDefinitionResponseDTO findByEntityCode(String entityCode){
        if (!repository.existsByEntityCode(entityCode)){
            throw new RuntimeException("There is no entity with this code: " + entityCode);
        }
        return mapper.toResponseDTO(repository.findByEntityCode(entityCode).orElseThrow(()->new RuntimeException("There is no entity with this code: " + entityCode)));
    }
}
