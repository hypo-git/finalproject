package com.finalproject.finalproject.repository;

import com.finalproject.finalproject.data.model.EntityDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntityDefinitionRepository extends JpaRepository<EntityDefinition, Long> {
    Optional<EntityDefinition> findByEntityCode(String entityCode);
    boolean existsByEntityCode(String entityCode);
}
