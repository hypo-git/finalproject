package com.finalproject.finalproject.data.dto.EntityDefinitionDTO;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class EntityDefinitionCreateDTO {
    @Column(nullable = false, unique = true)
    private String entityCode;

    @Column(nullable = false)
    private String entityName;
}
