package com.accountvault.repository;

import com.accountvault.model.AnalyticsEvent;
import com.accountvault.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AnalyticsEventRepository extends JpaRepository<AnalyticsEvent, Long> {
    Page<AnalyticsEvent> findByUser(User user, Pageable pageable);
    Page<AnalyticsEvent> findByEventType(String eventType, Pageable pageable);
    
    @Query("SELECT ae FROM AnalyticsEvent ae WHERE ae.timestamp BETWEEN :startDate AND :endDate")
    Page<AnalyticsEvent> findByTimestampBetween(@Param("startDate") LocalDateTime startDate, 
                                              @Param("endDate") LocalDateTime endDate, 
                                              Pageable pageable);
    
    @Query("SELECT COUNT(ae) FROM AnalyticsEvent ae WHERE ae.eventType = :eventType AND ae.timestamp BETWEEN :startDate AND :endDate")
    Long countByEventTypeAndTimestampBetween(@Param("eventType") String eventType, 
                                           @Param("startDate") LocalDateTime startDate, 
                                           @Param("endDate") LocalDateTime endDate);
}
