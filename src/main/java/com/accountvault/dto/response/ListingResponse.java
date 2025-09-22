package com.accountvault.dto.response;

import com.accountvault.model.AccountListing;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ListingResponse {
    private Long listingId;
    private UserResponse seller;
    private String title;
    private String description;
    private BigDecimal price;
    private String currency;
    private String category;
    private AccountListing.ListingStatus status;
    private Integer viewCount;
    private LocationResponse location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ListingResponse fromListing(AccountListing listing) {
        ListingResponse response = new ListingResponse();
        response.setListingId(listing.getListingId());
        response.setSeller(UserResponse.fromUser(listing.getSeller()));
        response.setTitle(listing.getTitle());
        response.setDescription(listing.getDescription());
        response.setPrice(listing.getPrice());
        response.setCurrency(listing.getCurrency());
        response.setCategory(listing.getCategory());
        response.setStatus(listing.getStatus());
        response.setViewCount(listing.getViewCount());
        if (listing.getLocation() != null) {
            response.setLocation(LocationResponse.fromLocation(listing.getLocation()));
        }
        response.setCreatedAt(listing.getCreatedAt());
        response.setUpdatedAt(listing.getUpdatedAt());
        return response;
    }
}
