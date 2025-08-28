package com.mongo.MongoDB.dto.request;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
public class InvoiceRequestDTO {
    @NotBlank(message = "User ID is required")
    @Pattern(regexp = "^[0-9a-fA-F]{24}$", message = "User ID must be a valid ObjectId")
    private String userId;

    @NotNull(message = "Items are required")
    @Size(min = 1, message = "At least one item is required")
    @Valid
    private List<ItemDTO> items;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "pending|paid|overdue", message = "Status must be one of: pending, paid, overdue")
    private String status;

    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date must be in the future or present")
    private Date dueDate;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be positive")
    private Double totalAmount;

    @Data
    public static class ItemDTO {
        @NotBlank(message = "Product name is required")
        private String productName;

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        private Integer quantity;
    }
}