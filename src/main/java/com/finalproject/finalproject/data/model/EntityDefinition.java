package com.finalproject.finalproject.data.model;

import com.finalproject.finalproject.data.enums.EntityType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
@Entity
@Table(name = "entity_definition")
public class EntityDefinition extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityType entityType = EntityType.EXTENSIBLE_CLASS;

    //this will be the table name
    @Column(nullable = false)
    @Size(min = 5, max = 65)
    private String entityCode;

    @Column(nullable = false)
    @Size(min = 5, max = 100)
    private String entityName;

    @Column(length = 2000)
    @Size(min = 5, max = 2000)
    private String description;

    @Column(length = 500)
    private String singularName;
    @Column(length = 500)
    private String pluralName;

    private String iconName;

}
