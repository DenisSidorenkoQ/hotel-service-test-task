package com.task.hotel_service_test_task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class HotelFullInfoDto {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private HotelAddressDto address;
    private HotelContactsDto contacts;
    private HotelArrivalTimeDto arrivalTime;
    private List<String> amenities;
}
