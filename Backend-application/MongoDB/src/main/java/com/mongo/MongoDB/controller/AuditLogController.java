package com.mongo.MongoDB.controller;

import com.mongo.MongoDB.dto.request.AuditLogRequestDTO;
import com.mongo.MongoDB.dto.response.ApiResponseDTO;
import com.mongo.MongoDB.dto.response.AuditLogResponseDTO;
import com.mongo.MongoDB.dto.mapper.AuditLogMapper;
import com.mongo.MongoDB.dto.response.UserResponseDTO;
import com.mongo.MongoDB.service.AuditLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;
    private final AuditLogMapper auditLogMapper;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<AuditLogResponseDTO>> createAuditLog(
            @Valid @RequestBody AuditLogRequestDTO auditLogRequest) {

        var auditLog = auditLogMapper.toEntity(auditLogRequest);
        var createdAuditLog = auditLogService.createAuditLog(auditLog);
        var response = auditLogMapper.toDTO(createdAuditLog);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.created(response, "/api/v1/audit-logs"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AuditLogResponseDTO>> getAuditLogById(@PathVariable String id) {
        var auditLog = auditLogService.getAuditLogById(id);
        // Handle the case where user is not found
        if (auditLog.isEmpty()) {
            ApiResponseDTO<AuditLogResponseDTO> response = new ApiResponseDTO<>();
            response.setStatus(404);
            response.setMessage("AuditLog not found with id: " + id);
            response.setPath("/api/v1/users/" + id);
            return ResponseEntity.status(404).body(response);
        }

        var response = auditLogMapper.toDTO(auditLog.get());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/audit-logs/" + id));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<AuditLogResponseDTO>>> getAllAuditLogs() {
        // Get all AuditLog without pagination
        var auditLogs  = auditLogService.getAllAuditLogs();
        // Convert to DTO list
        List<AuditLogResponseDTO> auditLogDTOs = auditLogs.stream()
                .map(auditLogMapper::toDTO)
                .collect(Collectors.toList());
        // Create response
        var response = ApiResponseDTO.success(auditLogDTOs, "/api/v1/audit-logs");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/entity-type/{entityType}")
    public ResponseEntity<ApiResponseDTO<List<AuditLogResponseDTO>>> getAuditLogsByEntityType(
            @PathVariable String entityType) {

        var auditLogs = auditLogService.getAuditLogsByEntityType(entityType);
        var response = auditLogs.stream()
                .map(auditLogMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/audit-logs/entity-type/" + entityType));
    }

    @GetMapping("/entity-id/{entityId}")
    public ResponseEntity<ApiResponseDTO<List<AuditLogResponseDTO>>> getAuditLogsByEntityId(
            @PathVariable String entityId) {

        var auditLogs = auditLogService.getAuditLogsByEntityId(entityId);
        var response = auditLogs.stream()
                .map(auditLogMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/audit-logs/entity-id/" + entityId));
    }

    @GetMapping("/action/{action}")
    public ResponseEntity<ApiResponseDTO<List<AuditLogResponseDTO>>> getAuditLogsByAction(@PathVariable String action) {
        var auditLogs = auditLogService.getAuditLogsByAction(action);
        var response = auditLogs.stream()
                .map(auditLogMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/audit-logs/action/" + action));
    }

    @GetMapping("/date-range")
    public ResponseEntity<ApiResponseDTO<List<AuditLogResponseDTO>>> getAuditLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        var auditLogs = auditLogService.getAuditLogsByDateRange(startDate, endDate);
        var response = auditLogs.stream()
                .map(auditLogMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/audit-logs/date-range"));
    }

    @GetMapping("/latest")
    public ResponseEntity<ApiResponseDTO<List<AuditLogResponseDTO>>> getLatestAuditLogs(
            @RequestParam(defaultValue = "10") int limit) {

        var auditLogs = auditLogService.getLatestAuditLogs(limit);
        var response = auditLogs.stream()
                .map(auditLogMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/audit-logs/latest"));
    }
}