package com.finalproject.finalproject.data.dto;

import com.finalproject.finalproject.data.enums.ChangeType;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeLogEntry {

    private String entityName; //"User"
    private Long entityId;     //user.id
    private ChangeType changeType; //CREATE/UPDATE/DELETE

    private Instant changedAt;
    private String changedBy;

    private Map<String, Object> oldValues;
    private Map<String, Object> newValues;
}
