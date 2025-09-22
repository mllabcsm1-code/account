package com.accountvault.repository;

import com.accountvault.model.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {
    @Query("SELECT sp FROM SubscriptionPlan sp ORDER BY sp.price ASC")
    List<SubscriptionPlan> findAllOrderByPriceAsc();
    
    @Query("SELECT sp FROM SubscriptionPlan sp WHERE sp.maxListings >= :minListings")
    List<SubscriptionPlan> findByMaxListingsGreaterThanEqual(Integer minListings);
}
