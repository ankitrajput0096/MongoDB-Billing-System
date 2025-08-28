package com.mongo.MongoDB.model;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.*;
import java.util.Date;

/**
 * Represents a financial transaction in the billing system.
 * Maps to the 'transactions' collection in MongoDB with schema validation.
 */
@Data  //@Data: Generates getters, setters, toString, equals, and hashCode methods
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;

    /**
     * Reference to the user who made this transaction
     * Required field with ObjectId format validation
     */
    @NotBlank(message = "User ID is required")
    @Pattern(regexp = "^[0-9a-fA-F]{24}$", message = "User ID must be a valid ObjectId")
    @Field("user_id")
    private ObjectId userId;

    /**
     * Transaction amount
     * Required field with positive value validation
     */
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
    private Double amount;

    /**
     * Current status of the transaction
     * Possible values: pending, completed, failed
     * Required field with enum validation
     */
    @NotBlank(message = "Status is required")
    @Pattern(regexp = "pending|completed|failed", message = "Status must be one of: pending, completed, failed")
    private String status;

    /**
     * Timestamp when the transaction occurred
     * Required field with past or present validation
     */
    @NotNull(message = "Timestamp is required")
    @PastOrPresent(message = "Timestamp must be in the past or present")
    private Date timestamp;

    /**
     * Date when the transaction record was created
     * Required field
     */
    @CreatedDate
    @NotNull(message = "Creation date is required")
    private Date createdAt;

    /**
     * Date when the transaction record was last updated
     * Required field
     */
    @LastModifiedDate
    @NotNull(message = "Update date is required")
    private Date updatedAt;

    /**
     * Additional metadata about the transaction
     * Required field with nested validation
     */
    @NotNull(message = "Metadata is required")
    @Valid
    private Metadata metadata;

    /**
     * Contains metadata about the transaction with validation
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Metadata {
        /**
         * Payment gateway used for the transaction
         * Required field
         */
        @NotBlank(message = "Gateway is required")
        private String gateway;

        /**
         * Unique transaction identifier from the payment gateway
         * Required field
         */
        @NotBlank(message = "Transaction ID is required")
        @Field("transaction_id")
        private String transactionId;
    }
}