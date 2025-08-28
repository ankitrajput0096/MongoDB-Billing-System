package com.mongo.MongoDB.dto.response;

import com.mongo.MongoDB.model.AuditLog;
import lombok.Data;
import java.util.Date;
import java.util.Map;

@Data
public class AuditLogResponseDTO {
    private String id;
    private String entityType;
    private String entityId;
    private String action;
    private Date timestamp;
    private Date createdAt;
    private Date updatedAt;
    private Map<String, Object> details;

    public static AuditLogResponseDTO fromEntity(AuditLog auditLog) {
        AuditLogResponseDTO dto = new AuditLogResponseDTO();
        dto.setId(auditLog.getId().toString());
        dto.setEntityType(auditLog.getEntityType());
        dto.setEntityId(auditLog.getEntityId().toString());
        dto.setAction(auditLog.getAction());
        dto.setTimestamp(auditLog.getTimestamp());
        dto.setCreatedAt(auditLog.getCreatedAt());
        dto.setUpdatedAt(auditLog.getUpdatedAt());
        dto.setDetails(auditLog.getDetails());
        return dto;
    }
}