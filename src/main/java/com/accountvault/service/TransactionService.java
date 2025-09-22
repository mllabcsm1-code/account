package com.accountvault.service;

import com.accountvault.model.Transaction;
import com.accountvault.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);
    Optional<Transaction> getTransactionById(Long transactionId);
    Transaction updateTransactionStatus(Long transactionId, Transaction.TransactionStatus status);
    Page<Transaction> getAllTransactions(Pageable pageable);
    Page<Transaction> getTransactionsByBuyer(User buyer, Pageable pageable);
    Page<Transaction> getTransactionsBySeller(User seller, Pageable pageable);
    Page<Transaction> getTransactionsByStatus(Transaction.TransactionStatus status, Pageable pageable);
    Page<Transaction> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    BigDecimal getTotalRevenueByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    Long getTransactionCountByUser(User user);
    Transaction completeTransaction(Long transactionId);
    Transaction refundTransaction(Long transactionId);
}
