package com.mongo.MongoDB.service;

import com.mongo.MongoDB.model.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AuditLogService {
    AuditLog createAuditLog(AuditLog auditLog);
    Optional<AuditLog> getAuditLogById(String id);
    List<AuditLog> getAllAuditLogs();
    Page<AuditLog> getAuditLogs(Pageable pageable);
    List<AuditLog> getAuditLogsByEntityType(String entityType);
    List<AuditLog> getAuditLogsByEntityId(String entityId);
    List<AuditLog> getAuditLogsByAction(String action);
    List<AuditLog> getAuditLogsByDateRange(Date startDate, Date endDate);
    List<AuditLog> getLatestAuditLogs(int limit);
    void logUserAction(String entityId, String action, Map<String, Object> details);
    void logTransactionAction(String entityId, String action, Map<String, Object> details);
}