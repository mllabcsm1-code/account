package com.accountvault.service.impl;

import com.accountvault.model.Transaction;
import com.accountvault.model.User;
import com.accountvault.repository.TransactionRepository;
import com.accountvault.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {
    
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setStatus(Transaction.TransactionStatus.PENDING);
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Transaction> getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }

    @Override
    public Transaction updateTransactionStatus(Long transactionId, Transaction.TransactionStatus status) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setStatus(status);
        if (status == Transaction.TransactionStatus.COMPLETED) {
            transaction.setCompletedAt(LocalDateTime.now());
        }
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Transaction> getAllTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Transaction> getTransactionsByBuyer(User buyer, Pageable pageable) {
        return transactionRepository.findByBuyer(buyer, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Transaction> getTransactionsBySeller(User seller, Pageable pageable) {
        return transactionRepository.findBySeller(seller, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Transaction> getTransactionsByStatus(Transaction.TransactionStatus status, Pageable pageable) {
        return transactionRepository.findByStatus(status, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Transaction> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return transactionRepository.findByCreatedAtBetween(startDate, endDate, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalRevenueByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal revenue = transactionRepository.getTotalRevenueByDateRange(startDate, endDate);
        return revenue != null ? revenue : BigDecimal.ZERO;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getTransactionCountByUser(User user) {
        return transactionRepository.countTransactionsByUser(user);
    }

    @Override
    public Transaction completeTransaction(Long transactionId) {
        return updateTransactionStatus(transactionId, Transaction.TransactionStatus.COMPLETED);
    }

    @Override
    public Transaction refundTransaction(Long transactionId) {
        return updateTransactionStatus(transactionId, Transaction.TransactionStatus.REFUNDED);
    }
}
