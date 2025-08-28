package com.mongo.MongoDB.dto.request;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.util.Date;
import java.util.Map;

@Data
public class AuditLogRequestDTO {
    @NotBlank(message = "Entity type is required")
    @Pattern(regexp = "user|transaction|invoice|subscription", message = "Entity type must be one of: user, transaction, invoice, subscription")
    private String entityType;

    @NotBlank(message = "Entity ID is required")
    @Pattern(regexp = "^[0-9a-fA-F]{24}$", message = "Entity ID must be a valid ObjectId")
    private String entityId;

    @NotBlank(message = "Action is required")
    private String action;

    @NotNull(message = "Timestamp is required")
    @PastOrPresent(message = "Timestamp must be in the past or present")
    private Date timestamp;

    private Map<String, Object> details;
}