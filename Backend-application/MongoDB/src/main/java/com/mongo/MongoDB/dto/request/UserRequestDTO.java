package com.mongo.MongoDB.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class UserRequestDTO {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Min(value = 0, message = "Age must be positive")
    @Max(value = 150, message = "Age must be realistic")
    private Integer age;

    @DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
    private Double balance;

    @NotNull(message = "Active status is required")
    private Boolean active;

    private List<@NotBlank(message = "Hobby cannot be blank") String> hobbies;

    @NotNull(message = "Billing address is required")
    @Valid
    private BillingAddressDTO billingAddress;

    @Data
    public static class BillingAddressDTO {
        @NotBlank(message = "City is required")
        private String city;

        @NotNull(message = "ZIP code is required")
        @Min(value = 0, message = "ZIP code must be positive")
        @Max(value = 99999, message = "ZIP code must be valid")
        private Integer zip;

        private String country;
    }
}