package com.accountvault.repository;

import com.accountvault.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByCountryAndCity(String country, String city);
    List<Location> findByCountry(String country);
    
    @Query("SELECT DISTINCT l.country FROM Location l ORDER BY l.country")
    List<String> findDistinctCountries();
    
    @Query("SELECT DISTINCT l.city FROM Location l WHERE l.country = :country ORDER BY l.city")
    List<String> findDistinctCitiesByCountry(@Param("country") String country);
}
