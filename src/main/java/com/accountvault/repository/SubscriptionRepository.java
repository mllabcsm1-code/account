package com.accountvault.repository;

import com.accountvault.model.Subscription;
import com.accountvault.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Page<Subscription> findByUser(User user, Pageable pageable);
    Page<Subscription> findByStatus(Subscription.SubscriptionStatus status, Pageable pageable);
    
    @Query("SELECT s FROM Subscription s WHERE s.user = :user AND s.status = 'ACTIVE'")
    Optional<Subscription> findActiveSubscriptionByUser(@Param("user") User user);
    
    @Query("SELECT s FROM Subscription s WHERE s.endDate < :currentDate AND s.status = 'ACTIVE'")
    Page<Subscription> findExpiredSubscriptions(@Param("currentDate") LocalDateTime currentDate, Pageable pageable);
    
    @Query("SELECT COUNT(s) FROM Subscription s WHERE s.status = :status")
    Long countByStatus(@Param("status") Subscription.SubscriptionStatus status);
}
