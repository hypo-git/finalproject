package com.finalproject.finalproject.service.metadata;

import com.finalproject.finalproject.data.dto.metadata.EntityDefinitionMetaDataDTO;
import com.finalproject.finalproject.data.dto.metadata.FieldMetaDataDTO;
import com.finalproject.finalproject.data.model.EntityDefinition;
import com.finalproject.finalproject.repository.EntityDefinitionRepository;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EntityDefinitionMetaDataService {

    private final EntityDefinitionRepository repository;

    //audit fields to be excluded
    private static final Set<String> EXCLUDED_FIELDS = Set.of(
            "id", "is_live", "created_at", "updated_at", "created_by", "updated_by"
    );

    public EntityDefinitionMetaDataDTO getEntityDefinitionMetaData(String entityCode) {
        EntityDefinition entityDefinition = repository.findByEntityCode(entityCode).orElseThrow(()->new RuntimeException("entity code not found"));

        List<FieldMetaDataDTO> fields = extractFields(entityDefinition.getClass());

        return EntityDefinitionMetaDataDTO.builder()
                .entityCode(entityDefinition.getEntityCode())
                .entityName(entityDefinition.getEntityName())
                .entityDescription(entityDefinition.getDescription())
                .fields(fields)
                .build();
    }

    private List<FieldMetaDataDTO> extractFields(Class<?> entityClass) {

        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> !EXCLUDED_FIELDS.contains(field.getName()))
                .map(field -> {
                    Column column = field.getAnnotation(Column.class);
                    Size size = field.getAnnotation(Size.class);
                    NotNull notNull = field.getAnnotation(NotNull.class);
                    Min min = field.getAnnotation(Min.class);
                    Max max = field.getAnnotation(Max.class);
                    Pattern  pattern = field.getAnnotation(Pattern.class);

                    return FieldMetaDataDTO.builder()
                            .fieldCode(field.getName())
                            .fieldName(field.getName())
                            .fieldType(field.getType().getSimpleName())

                            //JPA
                            .nullable(column == null || column.nullable())
                            .unique(column != null && column.unique())
                            .maxLength(column != null ? column.length() : null)

                            //BEAN
                            .minLength(size != null ? size.min() : null)
                            .maxLength(size != null ? size.max() : null)
                            .minValue(min != null ? min.value() : null)
                            .maxValue(max != null ? max.value() : null)
                            .pattern(pattern != null ? pattern.regexp() : null)

                            .build();
                }).toList();

    }
}
