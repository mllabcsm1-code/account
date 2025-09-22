package com.accountvault.service;

import com.accountvault.model.AccountListing;
import com.accountvault.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ListingService {
    AccountListing createListing(AccountListing listing);
    Optional<AccountListing> getListingById(Long listingId);
    AccountListing updateListing(Long listingId, AccountListing listing);
    void deleteListing(Long listingId);
    Page<AccountListing> getAllListings(Pageable pageable);
    Page<AccountListing> getListingsByStatus(AccountListing.ListingStatus status, Pageable pageable);
    Page<AccountListing> getListingsBySeller(User seller, Pageable pageable);
    Page<AccountListing> getListingsByCategory(String category, Pageable pageable);
    Page<AccountListing> getListingsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    Page<AccountListing> searchListings(String keyword, Pageable pageable);
    Page<AccountListing> getActiveListingsOrderByDate(Pageable pageable);
    Page<AccountListing> getActiveListingsOrderByViews(Pageable pageable);
    List<String> getAllCategories();
    AccountListing incrementViewCount(Long listingId);
}
