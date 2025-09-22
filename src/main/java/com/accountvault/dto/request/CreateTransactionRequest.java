package com.accountvault.dto.request;

import lombok.Data;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CreateTransactionRequest {
    @NotNull(message = "Listing ID is required")
    private Long listingId;

    @NotNull(message = "Buyer ID is required")
    private Long buyerId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    private String currency = "USD";
    private String paymentMethod;
    private String reason;
}
