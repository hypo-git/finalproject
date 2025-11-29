package com.finalproject.finalproject.data.dto.EntityDefinitionDTOs;

import com.finalproject.finalproject.data.enums.EntityType;
import lombok.Data;

@Data
public class EntityDefinitionUpdateDTO {
    private EntityType entityType;
    private String entityCode;
    private String entityName;
    private String iconName;
    private String singularName;
    private String pluralName;
    private String description;

}
