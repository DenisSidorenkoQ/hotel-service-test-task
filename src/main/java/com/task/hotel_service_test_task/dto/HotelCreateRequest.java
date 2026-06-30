package com.task.hotel_service_test_task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelCreateRequest {
    private String name;
    private String description;
    private String brand;
    private HotelAddressDto address;
    private HotelContactsDto contacts;
    private HotelArrivalTimeDto arrivalTime;
}
