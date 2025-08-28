package com.mongo.MongoDB.service.impl;

import com.mongo.MongoDB.exception.ResourceNotFoundException;
import com.mongo.MongoDB.model.AuditLog;
import com.mongo.MongoDB.repository.AuditLogRepository;
import com.mongo.MongoDB.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Override
    @Transactional
    public AuditLog createAuditLog(AuditLog auditLog) {
        // Set default values if not provided
        if (auditLog.getTimestamp() == null) {
            auditLog.setTimestamp(new Date());
        }

        if (auditLog.getCreatedAt() == null) {
            auditLog.setCreatedAt(new Date());
        }

        auditLog.setUpdatedAt(new Date());

        return auditLogRepository.save(auditLog);
    }

    @Override
    public Optional<AuditLog> getAuditLogById(String id) {
        return auditLogRepository.findById(id);
    }

    @Override
    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.findAll();
    }

    @Override
    public Page<AuditLog> getAuditLogs(Pageable pageable) {
        return auditLogRepository.findAll(pageable);
    }

    @Override
    public List<AuditLog> getAuditLogsByEntityType(String entityType) {
        return auditLogRepository.findByEntityType(entityType);
    }

    @Override
    public List<AuditLog> getAuditLogsByEntityId(String entityId) {
        return auditLogRepository.findByEntityId(new ObjectId(entityId));
    }

    @Override
    public List<AuditLog> getAuditLogsByAction(String action) {
        return auditLogRepository.findByAction(action);
    }

    @Override
    public List<AuditLog> getAuditLogsByDateRange(Date startDate, Date endDate) {
        return auditLogRepository.findByTimestampBetween(startDate, endDate);
    }

    @Override
    public List<AuditLog> getLatestAuditLogs(int limit) {
        return auditLogRepository.findTop10ByOrderByTimestampDesc();
    }

    @Override
    @Transactional
    public void logUserAction(String entityId, String action, Map<String, Object> details) {
        AuditLog auditLog = new AuditLog();
        auditLog.setEntityType("user");
        auditLog.setEntityId(new ObjectId(entityId));
        auditLog.setAction(action);
        auditLog.setDetails(details);

        createAuditLog(auditLog);
    }

    @Override
    @Transactional
    public void logTransactionAction(String entityId, String action, Map<String, Object> details) {
        AuditLog auditLog = new AuditLog();
        auditLog.setEntityType("transaction");
        auditLog.setEntityId(new ObjectId(entityId));
        auditLog.setAction(action);
        auditLog.setDetails(details);

        createAuditLog(auditLog);
    }
}