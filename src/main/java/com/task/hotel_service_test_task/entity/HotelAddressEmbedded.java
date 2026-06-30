package com.task.hotel_service_test_task.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Embeddable
public class HotelAddressEmbedded {
    private Integer houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;

    public HotelAddressEmbedded() {}

    @Override
    public String toString() {
        return String.format("%s %s, %s, %s, %s", houseNumber, street, city, postCode, country);
    }
}
