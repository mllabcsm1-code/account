package com.accountvault.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class CreateReviewRequest {
    @NotNull(message = "Reviewer ID is required")
    private Long reviewerId;

    @NotNull(message = "Reviewee ID is required")
    private Long revieweeId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    private String comment;
}
