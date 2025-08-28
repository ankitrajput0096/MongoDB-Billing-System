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
 * Represents a subscription in the billing system.
 * Maps to the 'subscriptions' collection in MongoDB with schema validation.
 */
@Data  //@Data: Generates getters, setters, toString, equals, and hashCode methods
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "subscriptions")
public class Subscription {

    @Id
    private ObjectId id;

    /**
     * Reference to the user who owns this subscription
     * Required field with ObjectId format validation
     */
    @NotBlank(message = "User ID is required")
    @Pattern(regexp = "^[0-9a-fA-F]{24}$", message = "User ID must be a valid ObjectId")
    @Field("user_id")
    private ObjectId userId;

    /**
     * Subscription plan details
     * Required field with nested validation
     */
    @NotNull(message = "Plan is required")
    @Valid
    private Plan plan;

    /**
     * Start date of the subscription
     * Required field with past or present validation
     */
    @NotNull(message = "Start date is required")
    @PastOrPresent(message = "Start date must be in the past or present")
    private Date startDate;

    /**
     * End date of the subscription (null for ongoing subscriptions)
     * Future validation if provided
     */
    @Future(message = "End date must be in the future")
    private Date endDate;

    /**
     * Current status of the subscription
     * Possible values: active, inactive, cancelled
     * Required field with enum validation
     */
    @NotBlank(message = "Status is required")
    @Pattern(regexp = "active|inactive|cancelled", message = "Status must be one of: active, inactive, cancelled")
    private String status;

    /**
     * Date when the subscription was created
     * Required field
     */
    @CreatedDate
    @NotNull(message = "Creation date is required")
    private Date createdAt;

    /**
     * Date when the subscription was last updated
     * Required field
     */
    @LastModifiedDate
    @NotNull(message = "Update date is required")
    private Date updatedAt;

    /**
     * Represents a subscription plan with validation
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Plan {
        /**
         * Name of the subscription plan
         * Required field
         */
        @NotBlank(message = "Plan name is required")
        private String planName;

        /**
         * Price of the subscription plan
         * Required field with positive validation
         */
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
        private Double price;

        /**
         * Billing cycle for the plan
         * Possible values: monthly, yearly
         * Required field with enum validation
         */
        @NotBlank(message = "Billing cycle is required")
        @Pattern(regexp = "monthly|yearly", message = "Billing cycle must be one of: monthly, yearly")
        private String billingCycle;
    }
}
