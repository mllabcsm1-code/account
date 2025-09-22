package com.accountvault.dto.response;

import com.accountvault.model.Review;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponse {
    private Long reviewId;
    private UserResponse reviewer;
    private UserResponse reviewee;
    private Integer rating;
    private String comment;
    private Boolean isVerified;
    private LocalDateTime createdAt;

    public static ReviewResponse fromReview(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.setReviewId(review.getReviewId());
        response.setReviewer(UserResponse.fromUser(review.getReviewer()));
        response.setReviewee(UserResponse.fromUser(review.getReviewee()));
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        response.setIsVerified(review.getIsVerified());
        response.setCreatedAt(review.getCreatedAt());
        return response;
    }
}
