package com.finalproject.finalproject.service;

import com.finalproject.finalproject.data.dto.ChangeLogEntry;
import com.finalproject.finalproject.data.enums.ChangeType;
import com.finalproject.finalproject.data.model.CustomRevisionEntity;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Type;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChangeLogService {
    private final EntityManager entityManager;
    private final ApplicationContext applicationContext;

    private Map<String, Class<?>> entityMap;

    @PostConstruct
    public void init() {
        entityMap = entityManager
                .getMetamodel()
                .getEntities()
                .stream()
                .map(Type::getJavaType)
                .filter(clazz -> clazz.isAnnotationPresent(org.hibernate.envers.Audited.class)) // Only audited entities
                .collect(Collectors.toMap(
                        Class::getSimpleName,
                        clazz -> clazz
                ));
    }

    @Transactional(readOnly = true)
    public List<ChangeLogEntry> getChangeLog(String entityName, Long entityId) {
        Class<?> entityClass = entityMap.get(entityName);
        if (entityClass == null) {
            throw new IllegalArgumentException("Entity not found: " + entityName + ". Available: " + entityMap.keySet());
        }

        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        List<Number> revisions = auditReader.getRevisions(entityClass, entityId);

        if (revisions.isEmpty()) {
            return Collections.emptyList();
        }

        List<ChangeLogEntry> changeLog = new ArrayList<>();

        for (int i = 0; i < revisions.size(); i++) {
            Number revision = revisions.get(i);

            Object entity = auditReader.find(entityClass, entityId, revision);
            CustomRevisionEntity revisionEntity = auditReader.findRevision(
                    CustomRevisionEntity.class,
                    revision
            );

            Object[] row = (Object[]) auditReader
                    .createQuery()
                    .forRevisionsOfEntity(entityClass, false, true)
                    .add(AuditEntity.id().eq(entityId))
                    .add(AuditEntity.revisionNumber().eq(revision))
                    .getSingleResult();
            //OMFG! this is just BS!
            RevisionType revType = (RevisionType) row[2];

            Object previousEntity = null;
            if(i > 0){
                previousEntity = auditReader.find(entityClass, entityId, revisions.get(i-1));
            }

            ChangeType changeType = switch (revType) {
                case ADD -> ChangeType.CREATE;
                case MOD -> ChangeType.UPDATE;
                case DEL -> ChangeType.DELETE;
            };

            Map<String, Object> oldValues = previousEntity != null
                    ? entityToMap(previousEntity)
                    : new HashMap<>();

            Map<String, Object> newValues = entity != null
                    ? entityToMap(entity)
                    : new HashMap<>();

            Instant timestamp = Instant.ofEpochMilli(revisionEntity.getRevtstmp());

            ChangeLogEntry entry = ChangeLogEntry.builder()
                    .entityName(entityName)
                    .entityId(entityId)
                    .changeType(changeType)
                    .changedAt(timestamp)
                    .changedBy(revisionEntity.getUserName())
                    .oldValues(oldValues)
                    .newValues(newValues)
                    .build();

            changeLog.add(entry);
        }

        Collections.reverse(changeLog);
        return changeLog;
    }

    @Transactional(readOnly = true)
    public List<ChangeLogEntry> getChangeLogByField(
            String entityName,
            String fieldName,
            Object fieldValue
    ) {
        Class<?> entityClass = entityMap.get(entityName);
        if (entityClass == null) {
            throw new IllegalArgumentException(
                    "Entity not found: " + entityName + ". Available: " + entityMap.keySet()
            );
        }

        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        List<Object[]> rows = auditReader.createQuery()
                .forRevisionsOfEntity(entityClass, false, true)
                .add(AuditEntity.property(fieldName).eq(fieldValue))
                .addOrder(AuditEntity.revisionNumber().asc())
                .getResultList();

        if (rows.isEmpty()) {
            return Collections.emptyList();
        }

        List<ChangeLogEntry> changeLog = new ArrayList<>();
        Object previousEntity = null;

        for (Object[] row : rows) {
            Object entity = row[0];
            CustomRevisionEntity revisionEntity = (CustomRevisionEntity) row[1];
            RevisionType revType = (RevisionType) row[2];

            ChangeType changeType = switch (revType) {
                case ADD -> ChangeType.CREATE;
                case MOD -> ChangeType.UPDATE;
                case DEL -> ChangeType.DELETE;
            };

            Map<String, Object> oldValues = previousEntity != null
                    ? entityToMap(previousEntity)
                    : new HashMap<>();

            Map<String, Object> newValues = entity != null
                    ? entityToMap(entity)
                    : new HashMap<>();

            ChangeLogEntry entry = ChangeLogEntry.builder()
                    .entityName(entityName)
                    .entityId(extractId(entity)) // see helper below
                    .changeType(changeType)
                    .changedAt(Instant.ofEpochMilli(revisionEntity.getRevtstmp()))
                    .changedBy(revisionEntity.getUserName())
                    .oldValues(oldValues)
                    .newValues(newValues)
                    .build();

            changeLog.add(entry);
            previousEntity = entity;
        }

        Collections.reverse(changeLog);
        return changeLog;
    }

    private Long extractId(Object entity) {
        try {
            for (var field : entity.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    return (Long) field.get(entity);
                }
            }
        } catch (Exception ignored) {}
        return null;
    }



    private Map<String, Object> entityToMap(Object entity) {
        Map<String, Object> map = new HashMap<>();

        try {
            java.lang.reflect.Field[] fields = entity.getClass().getDeclaredFields();
            for (java.lang.reflect.Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(entity);

                // Skip null, collections, password, and synthetic fields
                if (value != null &&
                        !(value instanceof Collection) &&
                        !field.getName().equals("password") &&
                        !field.getName().startsWith("$")) {
                    map.put(field.getName(), value.toString());
                }
            }
        } catch (Exception e) {
            // Log and continue
            e.printStackTrace();
        }

        return map;
    }

    public Set<String> getAvailableEntities() {
        return entityMap.keySet();
    }
}