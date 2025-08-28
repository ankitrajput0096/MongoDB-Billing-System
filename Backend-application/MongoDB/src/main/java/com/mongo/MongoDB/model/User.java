package com.mongo.MongoDB.model;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;
import jakarta.validation.constraints.*;

/**
 * Represents a user in the billing system.
 * Maps to the 'users' collection in MongoDB with schema validation.
 */
@Data  //@Data: Generates getters, setters, toString, equals, and hashCode methods
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    /**
     * Full name of the user
     * Required field with minimum length validation
     */
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    /**
     * Email address of the user (unique identifier)
     * Required field with email format validation
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    /**
     * Age of the user (optional field)
     * Minimum age validation if provided
     */
    @Min(value = 0, message = "Age must be positive")
    @Max(value = 150, message = "Age must be realistic")
    private Integer age;

    /**
     * Current account balance
     * Non-negative validation
     */
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
    private Double balance;

    /**
     * Indicates if the user account is active
     * Using @Field annotation because MongoDB field name is 'isActive'
     * while Java convention would be just 'active'
     * Required field with default value
     */
    @NotNull(message = "Active status is required")
    @Field("isActive")
    private Boolean active;

    /**
     * Date when the user account was created
     * Required field, automatically set to current date if not provided
     */
    @CreatedDate
    @NotNull(message = "Creation date is required")
    private Date createdAt;

    /**
     * Date when the user account was last updated
     * Required field, automatically set to current date if not provided
     */
    @LastModifiedDate
    @NotNull(message = "Update date is required")
    private Date updatedAt;

    /**
     * List of user's hobbies/interests
     */
    private List<@NotBlank(message = "Hobby cannot be blank") String> hobbies;

    /**
     * Billing address information
     * Required field with nested validation
     */
    @NotNull(message = "Billing address is required")
    @Valid
    private BillingAddress billingAddress;

    /**
     * Represents the billing address of a user with validation
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BillingAddress {
        /**
         * City name - required field
         */
        @NotBlank(message = "City is required")
        private String city;

        /**
         * ZIP/postal code - required field with range validation
         */
        @NotNull(message = "ZIP code is required")
        @Min(value = 0, message = "ZIP code must be positive")
        @Max(value = 99999, message = "ZIP code must be valid")
        private Integer zip;

        /**
         * Country name
         */
        private String country;
    }
}