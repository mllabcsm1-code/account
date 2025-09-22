package com.accountvault.dto.response;

import com.accountvault.model.Location;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationResponse {
    private Long locationId;
    private String country;
    private String city;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String coordinates;

    public static LocationResponse fromLocation(Location location) {
        LocationResponse response = new LocationResponse();
        response.setLocationId(location.getLocationId());
        response.setCountry(location.getCountry());
        response.setCity(location.getCity());
        response.setLatitude(location.getLatitude());
        response.setLongitude(location.getLongitude());
        response.setCoordinates(location.getCoordinates());
        return response;
    }
}
