package com.task.hotel_service_test_task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelArrivalTimeDto {
    private String checkIn;
    private String checkOut;
}
