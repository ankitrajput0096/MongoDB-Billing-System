package com.mongo.MongoDB.dto.request;

import lombok.Data;
import java.util.Date;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Data
public class TransactionRequestDTO {
    @NotBlank(message = "User ID is required")
    @Pattern(regexp = "^[0-9a-fA-F]{24}$", message = "User ID must be a valid ObjectId")
    private String userId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
    private Double amount;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "pending|completed|failed", message = "Status must be one of: pending, completed, failed")
    private String status;

    @NotNull(message = "Timestamp is required")
    @PastOrPresent(message = "Timestamp must be in the past or present")
    private Date timestamp;

    @NotNull(message = "Metadata is required")
    @Valid
    private MetadataDTO metadata;

    @Data
    public static class MetadataDTO {
        @NotBlank(message = "Gateway is required")
        private String gateway;

        @NotBlank(message = "Transaction ID is required")
        private String transactionId;
    }
}