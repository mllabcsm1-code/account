package com.accountvault.service;

import com.accountvault.model.Review;
import com.accountvault.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

public interface ReviewService {
    Review createReview(Review review);
    Optional<Review> getReviewById(Long reviewId);
    Page<Review> getReviewsByReviewee(User reviewee, Pageable pageable);
    Page<Review> getReviewsByReviewer(User reviewer, Pageable pageable);
    Page<Review> getReviewsByRating(Integer rating, Pageable pageable);
    BigDecimal getAverageRatingForUser(User user);
    Long getReviewCountForUser(User user);
    Review verifyReview(Long reviewId);
    void deleteReview(Long reviewId);
}
