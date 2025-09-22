package com.accountvault.service.impl;

import com.accountvault.model.Review;
import com.accountvault.model.User;
import com.accountvault.repository.ReviewRepository;
import com.accountvault.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {
    
    private final ReviewRepository reviewRepository;

    @Override
    public Review createReview(Review review) {
        review.setCreatedAt(LocalDateTime.now());
        review.setIsVerified(false);
        return reviewRepository.save(review);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Review> getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Review> getReviewsByReviewee(User reviewee, Pageable pageable) {
        return reviewRepository.findByReviewee(reviewee, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Review> getReviewsByReviewer(User reviewer, Pageable pageable) {
        return reviewRepository.findByReviewer(reviewer, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Review> getReviewsByRating(Integer rating, Pageable pageable) {
        return reviewRepository.findByRating(rating, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getAverageRatingForUser(User user) {
        BigDecimal average = reviewRepository.getAverageRatingForUser(user);
        return average != null ? average : BigDecimal.ZERO;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getReviewCountForUser(User user) {
        return reviewRepository.getReviewCountForUser(user);
    }

    @Override
    public Review verifyReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.setIsVerified(true);
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
