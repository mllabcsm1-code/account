package com.accountvault.controller;

import com.accountvault.model.Transaction;
import com.accountvault.model.User;
import com.accountvault.service.TransactionService;
import com.accountvault.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TransactionController {
    
    private final TransactionService transactionService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long transactionId) {
        Optional<Transaction> transaction = transactionService.getTransactionById(transactionId);
        return transaction.map(t -> ResponseEntity.ok(t))
                         .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{transactionId}/status")
    public ResponseEntity<Transaction> updateTransactionStatus(
            @PathVariable Long transactionId,
            @RequestParam Transaction.TransactionStatus status) {
        try {
            Transaction updatedTransaction = transactionService.updateTransactionStatus(transactionId, status);
            return ResponseEntity.ok(updatedTransaction);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<Transaction>> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactions = transactionService.getAllTransactions(pageable);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<Page<Transaction>> getTransactionsByBuyer(
            @PathVariable Long buyerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Optional<User> buyer = userService.getUserById(buyerId);
        if (buyer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactions = transactionService.getTransactionsByBuyer(buyer.get(), pageable);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<Page<Transaction>> getTransactionsBySeller(
            @PathVariable Long sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Optional<User> seller = userService.getUserById(sellerId);
        if (seller.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactions = transactionService.getTransactionsBySeller(seller.get(), pageable);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<Transaction>> getTransactionsByStatus(
            @PathVariable Transaction.TransactionStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactions = transactionService.getTransactionsByStatus(status, pageable);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/date-range")
    public ResponseEntity<Page<Transaction>> getTransactionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactions = transactionService.getTransactionsByDateRange(startDate, endDate, pageable);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/revenue")
    public ResponseEntity<BigDecimal> getTotalRevenue(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        BigDecimal revenue = transactionService.getTotalRevenueByDateRange(startDate, endDate);
        return ResponseEntity.ok(revenue);
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> getTransactionCountByUser(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Long count = transactionService.getTransactionCountByUser(user.get());
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{transactionId}/complete")
    public ResponseEntity<Transaction> completeTransaction(@PathVariable Long transactionId) {
        try {
            Transaction completedTransaction = transactionService.completeTransaction(transactionId);
            return ResponseEntity.ok(completedTransaction);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{transactionId}/refund")
    public ResponseEntity<Transaction> refundTransaction(@PathVariable Long transactionId) {
        try {
            Transaction refundedTransaction = transactionService.refundTransaction(transactionId);
            return ResponseEntity.ok(refundedTransaction);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
