package com.accountvault.repository;

import com.accountvault.model.Dispute;
import com.accountvault.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface DisputeRepository extends JpaRepository<Dispute, Long> {
    Page<Dispute> findByStatus(Dispute.DisputeStatus status, Pageable pageable);
    Page<Dispute> findByRaisedBy(User raisedBy, Pageable pageable);
    
    @Query("SELECT d FROM Dispute d WHERE d.transaction.buyer = :user OR d.transaction.listing.seller = :user")
    Page<Dispute> findByInvolvedUser(@Param("user") User user, Pageable pageable);
    
    @Query("SELECT d FROM Dispute d WHERE d.createdAt BETWEEN :startDate AND :endDate")
    Page<Dispute> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                       @Param("endDate") LocalDateTime endDate, 
                                       Pageable pageable);
    
    @Query("SELECT COUNT(d) FROM Dispute d WHERE d.status = :status")
    Long countByStatus(@Param("status") Dispute.DisputeStatus status);
}
