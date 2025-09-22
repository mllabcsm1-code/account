package com.accountvault.repository;

import com.accountvault.model.Review;
import com.accountvault.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByReviewee(User reviewee, Pageable pageable);
    Page<Review> findByReviewer(User reviewer, Pageable pageable);
    Page<Review> findByRating(Integer rating, Pageable pageable);
    Page<Review> findByIsVerified(Boolean isVerified, Pageable pageable);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.reviewee = :user")
    BigDecimal getAverageRatingForUser(@Param("user") User user);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.reviewee = :user")
    Long getReviewCountForUser(@Param("user") User user);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.reviewee = :user AND r.rating = :rating")
    Long getReviewCountByRating(@Param("user") User user, @Param("rating") Integer rating);
}
