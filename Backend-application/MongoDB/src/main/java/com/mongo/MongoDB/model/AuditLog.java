package com.mongo.MongoDB.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;
import java.util.Date;
import java.util.Map;

/**
 * Represents an audit log entry in the billing system.
 * Maps to the 'audit_logs' collection in MongoDB with schema validation.
 * Used for tracking changes to entities in the system.
 */
@Data  //@Data: Generates getters, setters, toString, equals, and hashCode methods
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "audit_logs")
public class AuditLog {

    @Id
    private ObjectId id;

    /**
     * Type of entity being audited
     * Possible values: user, transaction, invoice, subscription
     * Required field with enum validation
     */
    @NotBlank(message = "Entity type is required")
    @Pattern(regexp = "user|transaction|invoice|subscription", message = "Entity type must be one of: user, transaction, invoice, subscription")
    private String entityType;

    /**
     * ID of the entity being audited
     * Required field with ObjectId format validation
     */
    @NotBlank(message = "Entity ID is required")
    @Pattern(regexp = "^[0-9a-fA-F]{24}$", message = "Entity ID must be a valid ObjectId")
    private ObjectId entityId;

    /**
     * Action performed on the entity
     * Required field
     */
    @NotBlank(message = "Action is required")
    private String action;

    /**
     * Timestamp when the action occurred
     * Required field with past or present validation
     */
    @NotNull(message = "Timestamp is required")
    @PastOrPresent(message = "Timestamp must be in the past or present")
    private Date timestamp;

    /**
     * Date when the audit log was created
     * Required field
     */
    @CreatedDate
    @NotNull(message = "Creation date is required")
    private Date createdAt;

    /**
     * Date when the audit log was last updated
     * Required field
     */
    @LastModifiedDate
    @NotNull(message = "Update date is required")
    private Date updatedAt;

    /**
     * Additional details about the audit event
     * Contains information about who performed the action and what changed
     */
    private Map<String, Object> details;
}