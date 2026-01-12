package com.finalproject.finalproject.service;

import com.finalproject.finalproject.data.dto.EntityDefinitionDTOs.EntityDefinitionCreateDTO;
import com.finalproject.finalproject.data.dto.EntityDefinitionDTOs.EntityDefinitionResponseDTO;
import com.finalproject.finalproject.data.dto.EntityDefinitionDTOs.EntityDefinitionUpdateDTO;
import com.finalproject.finalproject.data.mapper.EntityDefinitionMapper;
import com.finalproject.finalproject.data.model.EntityDefinition;
import com.finalproject.finalproject.exception.EntityDefinitionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.finalproject.finalproject.repository.EntityDefinitionRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntityDefinitionService {

    private final EntityDefinitionRepository repository;
    private final EntityDefinitionMapper mapper;

    public EntityDefinitionResponseDTO getEntityDefinitionByCode(String entityCode){
        return mapper.toResponseDTO(findByCodeOrThrow(entityCode));
    }

    public EntityDefinitionResponseDTO getEntityDefinitionById(Long id){
        return mapper.toResponseDTO(findByIdOrThrow(id));
    }

    public List<EntityDefinitionResponseDTO> getAllEntityDefinitions(){
        return mapper.toResponseDTO(repository.findAll());
    }

    public EntityDefinitionResponseDTO createEntityDefinition(EntityDefinitionCreateDTO dto){
        if (repository.existsByEntityCode(dto.getEntityCode())){
            throw new EntityDefinitionException.EntityDefinitionAlreadyExistsException(dto.getEntityCode());
        }

        EntityDefinition entityDefinition = mapper.toEntity(dto);
        EntityDefinition saved = repository.save(entityDefinition);
        return mapper.toResponseDTO(saved);
    }

    public void deleteEntityDefinitionByCode(String entityCode){
        EntityDefinition entityDefinition = findByCodeOrThrow(entityCode);
        repository.delete(entityDefinition);
    }

    public void deleteEntityDefinitionById(Long id){
        EntityDefinition entityDefinition = findByIdOrThrow(id);
        repository.delete(entityDefinition);
    }

    //update
    public EntityDefinitionResponseDTO updateEntityDefinition(EntityDefinitionUpdateDTO dto){
        EntityDefinition entityDefinition = findByCodeOrThrow(dto.getEntityCode());
        mapper.updateEntity(entityDefinition, dto);
        return mapper.toResponseDTO(repository.save(entityDefinition));
    }


    public EntityDefinitionResponseDTO findByEntityCode(String entityCode){
        if (!repository.existsByEntityCode(entityCode)){
            throw new EntityDefinitionException.EntityDefinitionNotFoundException(entityCode);
        }
        return mapper.toResponseDTO(repository.findByEntityCode(entityCode).orElseThrow(()->new EntityDefinitionException.EntityDefinitionNotFoundException(entityCode)));
    }

    //helpers
    private EntityDefinition findByCodeOrThrow(String entityCode){
        log.debug("findByCodeOrThrow entityCode {}", entityCode);
        return repository.findByEntityCode(entityCode)
                .orElseThrow(()->new EntityDefinitionException.EntityDefinitionNotFoundException(entityCode));
    }
    private EntityDefinition findByIdOrThrow(Long id){
        log.debug("findByIdOrThrow id {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new EntityDefinitionException.EntityDefinitionNotFoundException(id));
    }

}
