package com.accountvault.repository;

import com.accountvault.model.AccountListing;
import com.accountvault.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountListingRepository extends JpaRepository<AccountListing, Long> {
    Page<AccountListing> findByStatus(AccountListing.ListingStatus status, Pageable pageable);
    Page<AccountListing> findBySeller(User seller, Pageable pageable);
    Page<AccountListing> findByCategory(String category, Pageable pageable);
    
    @Query("SELECT l FROM AccountListing l WHERE l.price BETWEEN :minPrice AND :maxPrice")
    Page<AccountListing> findByPriceBetween(@Param("minPrice") BigDecimal minPrice, 
                                          @Param("maxPrice") BigDecimal maxPrice, 
                                          Pageable pageable);
    
    @Query("SELECT l FROM AccountListing l WHERE l.title LIKE %:keyword% OR l.description LIKE %:keyword%")
    Page<AccountListing> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT l FROM AccountListing l WHERE l.status = 'ACTIVE' ORDER BY l.createdAt DESC")
    Page<AccountListing> findActiveListingsOrderByCreatedAtDesc(Pageable pageable);
    
    @Query("SELECT l FROM AccountListing l WHERE l.status = 'ACTIVE' ORDER BY l.viewCount DESC")
    Page<AccountListing> findActiveListingsOrderByViewCountDesc(Pageable pageable);
    
    @Query("SELECT DISTINCT l.category FROM AccountListing l WHERE l.status = 'ACTIVE'")
    List<String> findDistinctCategories();
}
