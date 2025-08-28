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
import java.util.List;

/**
 * Represents an invoice in the billing system.
 * Maps to the 'invoices' collection in MongoDB with schema validation.
 */
@Data  //@Data: Generates getters, setters, toString, equals, and hashCode methods
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "invoices")
public class Invoice {

    @Id
    private String id;

    /**
     * Reference to the user who owns this invoice
     * Required field with ObjectId format validation
     */
    @NotBlank(message = "User ID is required")
    @Pattern(regexp = "^[0-9a-fA-F]{24}$", message = "User ID must be a valid ObjectId")
    @Field("user_id")
    private ObjectId userId;

    /**
     * List of items included in the invoice
     * Required field with at least one item validation
     */
    @NotNull(message = "Items are required")
    @Size(min = 1, message = "At least one item is required")
    @Valid
    private List<Item> items;

    /**
     * Current status of the invoice
     * Possible values: pending, paid, overdue
     * Required field with enum validation
     */
    @NotBlank(message = "Status is required")
    @Pattern(regexp = "pending|paid|overdue", message = "Status must be one of: pending, paid, overdue")
    private String status;

    /**
     * Due date for the invoice payment
     * Required field with future or present validation
     */
    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date must be in the future or present")
    private Date dueDate;

    /**
     * Total amount of the invoice
     * Required field with positive validation
     */
    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be positive")
    @Field("totalAmount")
    private Double totalAmount;

    /**
     * Date when the invoice was created
     * Required field
     */
    @CreatedDate
    @NotNull(message = "Creation date is required")
    private Date createdAt;

    /**
     * Date when the invoice was last updated
     * Required field
     */
    @LastModifiedDate
    @NotNull(message = "Update date is required")
    private Date updatedAt;

    /**
     * Represents an item in an invoice with validation
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        /**
         * Name of the product/service
         * Required field
         */
        @NotBlank(message = "Product name is required")
        private String productName;

        /**
         * Quantity of the product/service
         * Required field with minimum value validation
         */
        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        private Integer quantity;
    }
}