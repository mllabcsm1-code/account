package com.accountvault.service.impl;

import com.accountvault.model.Dispute;
import com.accountvault.model.User;
import com.accountvault.repository.DisputeRepository;
import com.accountvault.service.DisputeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DisputeServiceImpl implements DisputeService {
    
    private final DisputeRepository disputeRepository;

    @Override
    public Dispute createDispute(Dispute dispute) {
        dispute.setCreatedAt(LocalDateTime.now());
        dispute.setStatus(Dispute.DisputeStatus.OPEN);
        return disputeRepository.save(dispute);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Dispute> getDisputeById(Long disputeId) {
        return disputeRepository.findById(disputeId);
    }

    @Override
    public Dispute updateDisputeStatus(Long disputeId, Dispute.DisputeStatus status) {
        Dispute dispute = disputeRepository.findById(disputeId)
                .orElseThrow(() -> new RuntimeException("Dispute not found"));
        dispute.setStatus(status);
        if (status == Dispute.DisputeStatus.RESOLVED || status == Dispute.DisputeStatus.CLOSED) {
            dispute.setResolvedAt(LocalDateTime.now());
        }
        return disputeRepository.save(dispute);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Dispute> getAllDisputes(Pageable pageable) {
        return disputeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Dispute> getDisputesByStatus(Dispute.DisputeStatus status, Pageable pageable) {
        return disputeRepository.findByStatus(status, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Dispute> getDisputesByUser(User user, Pageable pageable) {
        return disputeRepository.findByInvolvedUser(user, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Dispute> getDisputesByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return disputeRepository.findByCreatedAtBetween(startDate, endDate, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getDisputeCountByStatus(Dispute.DisputeStatus status) {
        return disputeRepository.countByStatus(status);
    }

    @Override
    public Dispute resolveDispute(Long disputeId) {
        return updateDisputeStatus(disputeId, Dispute.DisputeStatus.RESOLVED);
    }

    @Override
    public Dispute escalateDispute(Long disputeId) {
        return updateDisputeStatus(disputeId, Dispute.DisputeStatus.ESCALATED);
    }
}
