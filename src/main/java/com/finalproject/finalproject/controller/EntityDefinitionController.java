package com.finalproject.finalproject.controller;

import com.finalproject.finalproject.data.dto.EntityDefinitionDTOs.EntityDefinitionCreateDTO;
import com.finalproject.finalproject.data.dto.EntityDefinitionDTOs.EntityDefinitionResponseDTO;
import com.finalproject.finalproject.data.dto.metadata.EntityDefinitionMetaDataDTO;
import com.finalproject.finalproject.service.metadata.EntityDefinitionMetaDataService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.finalproject.finalproject.service.EntityDefinitionService;

import java.util.List;

@RestController
@RequestMapping("/api/entity-definitions")
@AllArgsConstructor
public class EntityDefinitionController {

    private final EntityDefinitionService entityDefinitionService;
    private final EntityDefinitionMetaDataService entityDefinitionMetaDataService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public EntityDefinitionResponseDTO createEntity(@Valid @RequestBody EntityDefinitionCreateDTO dto){
        return entityDefinitionService.createEntityDefinition(dto);
    }

    @PutMapping("/deleteById/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public void deleteEntityDefinitionById(@PathVariable Long id){
        entityDefinitionService.deleteEntityDefinitionById(id);
    }

    @PutMapping("/deleteByCode/{entityCode}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public void deleteEntityDefinitionByCode(@PathVariable String entityCode){
        entityDefinitionService.deleteEntityDefinitionByCode(entityCode);
    }

    @GetMapping("/entity-definition/{entityCode}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public EntityDefinitionResponseDTO getEntityDefinitionByCode(@PathVariable String entityCode){
        return entityDefinitionService.getEntityDefinitionByCode(entityCode);
    }

    @GetMapping("/entity-definition/{entityId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public EntityDefinitionResponseDTO getEntityDefinitionById(@PathVariable Long entityId){
        return entityDefinitionService.getEntityDefinitionById(entityId);
    }

    @GetMapping("/allEntities")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public List<EntityDefinitionResponseDTO> getAllEntityDefinitions(){
        return entityDefinitionService.getAllEntityDefinitions();
    }

    @GetMapping("/metadata/{entityCode}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public EntityDefinitionMetaDataDTO getEntityDefinitionMetaData(@PathVariable String entityCode){
        return entityDefinitionMetaDataService.getEntityDefinitionMetaData(entityCode);
    }

}
