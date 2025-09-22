package com.accountvault.repository;

import com.accountvault.model.Escrow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface EscrowRepository extends JpaRepository<Escrow, Long> {
    Page<Escrow> findByStatus(Escrow.EscrowStatus status, Pageable pageable);
    
    @Query("SELECT e FROM Escrow e WHERE e.createdAt BETWEEN :startDate AND :endDate")
    Page<Escrow> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                      @Param("endDate") LocalDateTime endDate, 
                                      Pageable pageable);
    
    @Query("SELECT SUM(e.amount) FROM Escrow e WHERE e.status = 'HELD'")
    BigDecimal getTotalHeldAmount();
    
    @Query("SELECT COUNT(e) FROM Escrow e WHERE e.status = :status")
    Long countByStatus(@Param("status") Escrow.EscrowStatus status);
}
