package com.accountvault.controller;

import com.accountvault.model.AccountListing;
import com.accountvault.model.User;
import com.accountvault.service.ListingService;
import com.accountvault.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/listings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ListingController {
    
    private final ListingService listingService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<AccountListing> createListing(@RequestBody AccountListing listing) {
        AccountListing createdListing = listingService.createListing(listing);
        return new ResponseEntity<>(createdListing, HttpStatus.CREATED);
    }

    @GetMapping("/{listingId}")
    public ResponseEntity<AccountListing> getListingById(@PathVariable Long listingId) {
        Optional<AccountListing> listing = listingService.getListingById(listingId);
        if (listing.isPresent()) {
            // Increment view count when listing is viewed
            listingService.incrementViewCount(listingId);
            return ResponseEntity.ok(listing.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{listingId}")
    public ResponseEntity<AccountListing> updateListing(
            @PathVariable Long listingId, 
            @RequestBody AccountListing listing) {
        try {
            AccountListing updatedListing = listingService.updateListing(listingId, listing);
            return ResponseEntity.ok(updatedListing);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{listingId}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long listingId) {
        listingService.deleteListing(listingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<AccountListing>> getAllListings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AccountListing> listings = listingService.getAllListings(pageable);
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<AccountListing>> getListingsByStatus(
            @PathVariable AccountListing.ListingStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AccountListing> listings = listingService.getListingsByStatus(status, pageable);
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<Page<AccountListing>> getListingsBySeller(
            @PathVariable Long sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Optional<User> seller = userService.getUserById(sellerId);
        if (seller.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<AccountListing> listings = listingService.getListingsBySeller(seller.get(), pageable);
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<AccountListing>> getListingsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AccountListing> listings = listingService.getListingsByCategory(category, pageable);
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/price-range")
    public ResponseEntity<Page<AccountListing>> getListingsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AccountListing> listings = listingService.getListingsByPriceRange(minPrice, maxPrice, pageable);
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AccountListing>> searchListings(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AccountListing> listings = listingService.searchListings(keyword, pageable);
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/active/recent")
    public ResponseEntity<Page<AccountListing>> getActiveListingsOrderByDate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AccountListing> listings = listingService.getActiveListingsOrderByDate(pageable);
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/active/popular")
    public ResponseEntity<Page<AccountListing>> getActiveListingsOrderByViews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AccountListing> listings = listingService.getActiveListingsOrderByViews(pageable);
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = listingService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
