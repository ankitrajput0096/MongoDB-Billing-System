package com.mongo.MongoDB.dto.mapper;

import com.mongo.MongoDB.dto.request.AuditLogRequestDTO;
import com.mongo.MongoDB.dto.response.AuditLogResponseDTO;
import com.mongo.MongoDB.model.AuditLog;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class AuditLogMapper {

    public AuditLog toEntity(AuditLogRequestDTO dto) {
        AuditLog auditLog = new AuditLog();
        auditLog.setEntityType(dto.getEntityType());
        auditLog.setEntityId(new ObjectId(dto.getEntityId()));
        auditLog.setAction(dto.getAction());
        auditLog.setTimestamp(dto.getTimestamp());
        auditLog.setDetails(dto.getDetails());
        return auditLog;
    }

    public AuditLogResponseDTO toDTO(AuditLog auditLog) {
        return AuditLogResponseDTO.fromEntity(auditLog);
    }
}