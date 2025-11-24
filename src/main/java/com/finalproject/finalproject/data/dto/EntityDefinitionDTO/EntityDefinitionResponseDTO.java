package com.finalproject.finalproject.data.dto.EntityDefinitionDTO;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class EntityDefinitionResponseDTO {
    private String entityCode;
    private String entityName;
}
