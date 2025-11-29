package com.finalproject.finalproject.data.dto.metadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldMetaDataDTO {
    private String fieldCode;
    private String fieldName;
    private String fieldType;
    private boolean nullable;
    private boolean unique;
    private Integer minLength;
    private Integer maxLength;
    private Long minValue;
    private Long maxValue;
    private String pattern;
}
