package com.accountvault.service;

import com.accountvault.model.Dispute;
import com.accountvault.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DisputeService {
    Dispute createDispute(Dispute dispute);
    Optional<Dispute> getDisputeById(Long disputeId);
    Dispute updateDisputeStatus(Long disputeId, Dispute.DisputeStatus status);
    Page<Dispute> getAllDisputes(Pageable pageable);
    Page<Dispute> getDisputesByStatus(Dispute.DisputeStatus status, Pageable pageable);
    Page<Dispute> getDisputesByUser(User user, Pageable pageable);
    Page<Dispute> getDisputesByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Long getDisputeCountByStatus(Dispute.DisputeStatus status);
    Dispute resolveDispute(Long disputeId);
    Dispute escalateDispute(Long disputeId);
}
