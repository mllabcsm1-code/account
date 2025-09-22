package com.accountvault.repository;

import com.accountvault.model.ModerationQueue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModerationQueueRepository extends JpaRepository<ModerationQueue, Long> {
    Page<ModerationQueue> findByStatus(ModerationQueue.ModerationStatus status, Pageable pageable);
    Page<ModerationQueue> findByEntityType(String entityType, Pageable pageable);
    
    @Query("SELECT mq FROM ModerationQueue mq WHERE mq.status = 'PENDING' ORDER BY mq.aiScore DESC")
    Page<ModerationQueue> findPendingOrderByAiScoreDesc(Pageable pageable);
    
    @Query("SELECT COUNT(mq) FROM ModerationQueue mq WHERE mq.status = :status")
    Long countByStatus(@Param("status") ModerationQueue.ModerationStatus status);
}
