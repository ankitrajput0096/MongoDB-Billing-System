package com.mongo.MongoDB.repository;

import com.mongo.MongoDB.model.AuditLog;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLog, String> {

    // Find audit logs by entity type
    List<AuditLog> findByEntityType(String entityType);

    // Find audit logs by entity ID
    List<AuditLog> findByEntityId(ObjectId entityId);

    // Find audit logs by action
    List<AuditLog> findByAction(String action);

    // Find audit logs by timestamp range
    List<AuditLog> findByTimestampBetween(Date startDate, Date endDate);

    // Find audit logs by entity type and entity ID
    List<AuditLog> findByEntityTypeAndEntityId(String entityType, String entityId);

    // Find audit logs by entity type and action
    List<AuditLog> findByEntityTypeAndAction(String entityType, String action);

    // Find audit logs for a specific user's actions
    @Query("{ 'details.performedBy': ?0 }")
    List<AuditLog> findByPerformedBy(String performedBy);

    // Find latest audit logs with pagination
    List<AuditLog> findTop10ByOrderByTimestampDesc();

    // Count audit logs by entity type
    Long countByEntityType(String entityType);

    // Count audit logs by action
    Long countByAction(String action);

    // Find audit logs with specific change details
    @Query("{ 'details.changes': { $exists: true } }")
    List<AuditLog> findLogsWithChanges();

    // Find audit logs for a specific time period and entity type
    List<AuditLog> findByEntityTypeAndTimestampBetween(String entityType, Date startDate, Date endDate);
}