package com.finalproject.finalproject.controller;

import com.finalproject.finalproject.data.dto.ChangeLogEntry;
import com.finalproject.finalproject.service.ChangeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/changelog")
@RequiredArgsConstructor
public class ChangeLogController {

    private final ChangeLogService changeLogService;

    @GetMapping("/{entity}/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public List<ChangeLogEntry> getChangeLog(
            @PathVariable String entity,
            @PathVariable Long id
    ){
        return changeLogService.getChangeLog(entity, id);
    }

    // NEW: field-based endpoint
    @GetMapping("/{entity}/by-field")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public List<ChangeLogEntry> getChangeLogByField(
            @PathVariable String entity,
            @RequestParam String field,
            @RequestParam String value
    ) {
        return changeLogService.getChangeLogByField(entity, field, value);
    }

    @GetMapping("/entities")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public Set<String> getEntities(){
        return changeLogService.getAvailableEntities();
    }
}
