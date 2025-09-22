package com.accountvault.controller;

import com.accountvault.model.Dispute;
import com.accountvault.model.User;
import com.accountvault.service.DisputeService;
import com.accountvault.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/disputes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DisputeController {
    
    private final DisputeService disputeService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Dispute> createDispute(@RequestBody Dispute dispute) {
        Dispute createdDispute = disputeService.createDispute(dispute);
        return new ResponseEntity<>(createdDispute, HttpStatus.CREATED);
    }

    @GetMapping("/{disputeId}")
    public ResponseEntity<Dispute> getDisputeById(@PathVariable Long disputeId) {
        Optional<Dispute> dispute = disputeService.getDisputeById(disputeId);
        return dispute.map(d -> ResponseEntity.ok(d))
                     .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{disputeId}/status")
    public ResponseEntity<Dispute> updateDisputeStatus(
            @PathVariable Long disputeId,
            @RequestParam Dispute.DisputeStatus status) {
        try {
            Dispute updatedDispute = disputeService.updateDisputeStatus(disputeId, status);
            return ResponseEntity.ok(updatedDispute);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<Dispute>> getAllDisputes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Dispute> disputes = disputeService.getAllDisputes(pageable);
        return ResponseEntity.ok(disputes);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<Dispute>> getDisputesByStatus(
            @PathVariable Dispute.DisputeStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Dispute> disputes = disputeService.getDisputesByStatus(status, pageable);
        return ResponseEntity.ok(disputes);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Dispute>> getDisputesByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Dispute> disputes = disputeService.getDisputesByUser(user.get(), pageable);
        return ResponseEntity.ok(disputes);
    }

    @GetMapping("/date-range")
    public ResponseEntity<Page<Dispute>> getDisputesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Dispute> disputes = disputeService.getDisputesByDateRange(startDate, endDate, pageable);
        return ResponseEntity.ok(disputes);
    }

    @GetMapping("/status/{status}/count")
    public ResponseEntity<Long> getDisputeCountByStatus(@PathVariable Dispute.DisputeStatus status) {
        Long count = disputeService.getDisputeCountByStatus(status);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{disputeId}/resolve")
    public ResponseEntity<Dispute> resolveDispute(@PathVariable Long disputeId) {
        try {
            Dispute resolvedDispute = disputeService.resolveDispute(disputeId);
            return ResponseEntity.ok(resolvedDispute);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{disputeId}/escalate")
    public ResponseEntity<Dispute> escalateDispute(@PathVariable Long disputeId) {
        try {
            Dispute escalatedDispute = disputeService.escalateDispute(disputeId);
            return ResponseEntity.ok(escalatedDispute);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
