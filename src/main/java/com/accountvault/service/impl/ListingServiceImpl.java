package com.accountvault.service.impl;

import com.accountvault.model.AccountListing;
import com.accountvault.model.User;
import com.accountvault.repository.AccountListingRepository;
import com.accountvault.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ListingServiceImpl implements ListingService {
    
    private final AccountListingRepository listingRepository;

    @Override
    public AccountListing createListing(AccountListing listing) {
        listing.setCreatedAt(LocalDateTime.now());
        listing.setStatus(AccountListing.ListingStatus.ACTIVE);
        listing.setViewCount(0);
        return listingRepository.save(listing);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountListing> getListingById(Long listingId) {
        return listingRepository.findById(listingId);
    }

    @Override
    public AccountListing updateListing(Long listingId, AccountListing listing) {
        AccountListing existingListing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Listing not found"));
        
        existingListing.setTitle(listing.getTitle());
        existingListing.setDescription(listing.getDescription());
        existingListing.setPrice(listing.getPrice());
        existingListing.setCurrency(listing.getCurrency());
        existingListing.setCategory(listing.getCategory());
        existingListing.setUpdatedAt(LocalDateTime.now());
        
        return listingRepository.save(existingListing);
    }

    @Override
    public void deleteListing(Long listingId) {
        listingRepository.deleteById(listingId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountListing> getAllListings(Pageable pageable) {
        return listingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountListing> getListingsByStatus(AccountListing.ListingStatus status, Pageable pageable) {
        return listingRepository.findByStatus(status, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountListing> getListingsBySeller(User seller, Pageable pageable) {
        return listingRepository.findBySeller(seller, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountListing> getListingsByCategory(String category, Pageable pageable) {
        return listingRepository.findByCategory(category, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountListing> getListingsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return listingRepository.findByPriceBetween(minPrice, maxPrice, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountListing> searchListings(String keyword, Pageable pageable) {
        return listingRepository.findByKeyword(keyword, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountListing> getActiveListingsOrderByDate(Pageable pageable) {
        return listingRepository.findActiveListingsOrderByCreatedAtDesc(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountListing> getActiveListingsOrderByViews(Pageable pageable) {
        return listingRepository.findActiveListingsOrderByViewCountDesc(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllCategories() {
        return listingRepository.findDistinctCategories();
    }

    @Override
    public AccountListing incrementViewCount(Long listingId) {
        AccountListing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Listing not found"));
        listing.setViewCount(listing.getViewCount() + 1);
        return listingRepository.save(listing);
    }
}
