package com.finalproject.finalproject.controller;

import com.finalproject.finalproject.data.dto.EntityDefinitionDTO.EntityDefinitionCreateDTO;
import com.finalproject.finalproject.data.dto.EntityDefinitionDTO.EntityDefinitionResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.finalproject.finalproject.service.EntityDefinitionService;

@RestController
@RequestMapping("/api/entity-definitions")
@AllArgsConstructor
public class EntityDefinitionController {

    private final EntityDefinitionService entityDefinitionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public EntityDefinitionResponseDTO createEntity(@Valid @RequestBody EntityDefinitionCreateDTO dto){
        return entityDefinitionService.createEntityDefinition(dto);
    }

}
