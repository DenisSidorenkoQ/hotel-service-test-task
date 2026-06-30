package com.task.hotel_service_test_task.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class HotelArrivalTimeEmbedded {
    private String checkIn;
    private String checkOut;
}