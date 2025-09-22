package com.accountvault.controller;

import com.accountvault.model.Review;
import com.accountvault.model.User;
import com.accountvault.service.ReviewService;
import com.accountvault.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReviewController {
    
    private final ReviewService reviewService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review createdReview = reviewService.createReview(review);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        Optional<Review> review = reviewService.getReviewById(reviewId);
        return review.map(r -> ResponseEntity.ok(r))
                    .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/reviewee/{revieweeId}")
    public ResponseEntity<Page<Review>> getReviewsByReviewee(
            @PathVariable Long revieweeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Optional<User> reviewee = userService.getUserById(revieweeId);
        if (reviewee.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewService.getReviewsByReviewee(reviewee.get(), pageable);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/reviewer/{reviewerId}")
    public ResponseEntity<Page<Review>> getReviewsByReviewer(
            @PathVariable Long reviewerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Optional<User> reviewer = userService.getUserById(reviewerId);
        if (reviewer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewService.getReviewsByReviewer(reviewer.get(), pageable);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<Page<Review>> getReviewsByRating(
            @PathVariable Integer rating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewService.getReviewsByRating(rating, pageable);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/user/{userId}/average-rating")
    public ResponseEntity<BigDecimal> getAverageRatingForUser(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        BigDecimal averageRating = reviewService.getAverageRatingForUser(user.get());
        return ResponseEntity.ok(averageRating);
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> getReviewCountForUser(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Long count = reviewService.getReviewCountForUser(user.get());
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{reviewId}/verify")
    public ResponseEntity<Review> verifyReview(@PathVariable Long reviewId) {
        try {
            Review verifiedReview = reviewService.verifyReview(reviewId);
            return ResponseEntity.ok(verifiedReview);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
