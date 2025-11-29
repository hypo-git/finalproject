package com.finalproject.finalproject.service;

import com.finalproject.finalproject.data.dto.EntityDefinitionDTOs.EntityDefinitionCreateDTO;
import com.finalproject.finalproject.data.dto.EntityDefinitionDTOs.EntityDefinitionResponseDTO;
import com.finalproject.finalproject.data.mapper.EntityDefinitionMapper;
import com.finalproject.finalproject.data.model.EntityDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.finalproject.finalproject.repository.EntityDefinitionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntityDefinitionService {

    private final EntityDefinitionRepository repository;
    private final EntityDefinitionMapper mapper;

    public EntityDefinitionResponseDTO getEntityDefinitionByCode(String entityCode){
        EntityDefinition entityDefinition = repository.findByEntityCode(entityCode).orElseThrow(()->new RuntimeException("EntityDefinition not found"));
        return mapper.toResponseDTO(entityDefinition);
    }

    public EntityDefinitionResponseDTO getEntityDefinitionById(Long id){
        EntityDefinition entityDefinition = repository.findById(id).orElseThrow(()->new RuntimeException("EntityDefinition not found"));
        return mapper.toResponseDTO(entityDefinition);
    }

    public List<EntityDefinitionResponseDTO> getAllEntityDefinitions(){
        List<EntityDefinition> entityDefinitions = repository.findAll();
        return mapper.toResponseDTO(entityDefinitions);
    }

    public EntityDefinitionResponseDTO createEntityDefinition(EntityDefinitionCreateDTO dto){
        if (repository.existsByEntityCode(dto.getEntityCode())){
            throw new RuntimeException("There is an entity with this code: " + dto.getEntityCode());
        }

        EntityDefinition entityDefinition = mapper.toEntity(dto);
        EntityDefinition saved = repository.save(entityDefinition);
        return mapper.toResponseDTO(saved);
    }
    //soft-delete by code
    public void deleteEntityDefinitionByCode(String entityCode){
        EntityDefinition entityDefinition = repository.findByEntityCode(entityCode).orElseThrow(() -> new RuntimeException("There is no entity with this code: " + entityCode));
        entityDefinition.setLive(false);
        repository.save(entityDefinition);
    }
    //soft-delete by id
    public void deleteEntityDefinitionById(Long id){
        EntityDefinition entityDefinition = repository.findById(id).orElseThrow(() -> new RuntimeException("There is no entity with this id: " + id));
        entityDefinition.setLive(false);
        repository.save(entityDefinition);
    }

    public EntityDefinitionResponseDTO findByEntityCode(String entityCode){
        if (!repository.existsByEntityCode(entityCode)){
            throw new RuntimeException("There is no entity with this code: " + entityCode);
        }
        return mapper.toResponseDTO(repository.findByEntityCode(entityCode).orElseThrow(()->new RuntimeException("There is no entity with this code: " + entityCode)));
    }
}
