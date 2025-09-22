package com.accountvault.dto.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class CreateDisputeRequest {
    @NotNull(message = "Transaction ID is required")
    private Long transactionId;

    @NotNull(message = "User ID is required")
    private Long raisedById;

    @NotBlank(message = "Reason is required")
    private String reason;

    private String evidenceUrl;
}
