package com.mongo.MongoDB.dto.request;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.Date;

@Data
public class SubscriptionRequestDTO {
    @NotBlank(message = "User ID is required")
    @Pattern(regexp = "^[0-9a-fA-F]{24}$", message = "User ID must be a valid ObjectId")
    private String userId;

    @NotNull(message = "Plan is required")
    @Valid
    private PlanDTO plan;

    @NotNull(message = "Start date is required")
    @PastOrPresent(message = "Start date must be in the past or present")
    private Date startDate;

    @Future(message = "End date must be in the future")
    private Date endDate;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "active|inactive|cancelled", message = "Status must be one of: active, inactive, cancelled")
    private String status;

    @Data
    public static class PlanDTO {
        @NotBlank(message = "Plan name is required")
        private String planName;

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
        private Double price;

        @NotBlank(message = "Billing cycle is required")
        @Pattern(regexp = "monthly|yearly", message = "Billing cycle must be one of: monthly, yearly")
        private String billingCycle;
    }
}