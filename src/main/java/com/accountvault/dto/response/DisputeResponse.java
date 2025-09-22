package com.accountvault.dto.response;

import com.accountvault.model.Dispute;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DisputeResponse {
    private Long disputeId;
    private TransactionResponse transaction;
    private UserResponse raisedBy;
    private String reason;
    private Dispute.DisputeStatus status;
    private String evidenceUrl;
    private LocalDateTime resolvedAt;
    private LocalDateTime createdAt;

    public static DisputeResponse fromDispute(Dispute dispute) {
        DisputeResponse response = new DisputeResponse();
        response.setDisputeId(dispute.getDisputeId());
        response.setTransaction(TransactionResponse.fromTransaction(dispute.getTransaction()));
        response.setRaisedBy(UserResponse.fromUser(dispute.getRaisedBy()));
        response.setReason(dispute.getReason());
        response.setStatus(dispute.getStatus());
        response.setEvidenceUrl(dispute.getEvidenceUrl());
        response.setResolvedAt(dispute.getResolvedAt());
        response.setCreatedAt(dispute.getCreatedAt());
        return response;
    }
}
