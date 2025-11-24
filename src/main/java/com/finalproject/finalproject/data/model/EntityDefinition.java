package com.finalproject.finalproject.data.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "entity_definition")
public class EntityDefinition extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String entityCode;

    @Column(nullable = false)
    private String entityName;

}
