package com.accountvault.dto.response;

import com.accountvault.model.Transaction;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private Long transactionId;
    private ListingResponse listing;
    private UserResponse buyer;
    private BigDecimal amount;
    private String currency;
    private Transaction.TransactionStatus status;
    private String paymentMethod;
    private String reason;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;

    public static TransactionResponse fromTransaction(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setTransactionId(transaction.getTransactionId());
        response.setListing(ListingResponse.fromListing(transaction.getListing()));
        response.setBuyer(UserResponse.fromUser(transaction.getBuyer()));
        response.setAmount(transaction.getAmount());
        response.setCurrency(transaction.getCurrency());
        response.setStatus(transaction.getStatus());
        response.setPaymentMethod(transaction.getPaymentMethod());
        response.setReason(transaction.getReason());
        response.setCompletedAt(transaction.getCompletedAt());
        response.setCreatedAt(transaction.getCreatedAt());
        return response;
    }
}
