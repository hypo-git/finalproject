package com.finalproject.finalproject.data.dto.metadata;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityDefinitionMetaDataDTO {
    private String entityCode;
    private String entityName;
    private String entityDescription;
    private List<FieldMetaDataDTO> fields;
}
