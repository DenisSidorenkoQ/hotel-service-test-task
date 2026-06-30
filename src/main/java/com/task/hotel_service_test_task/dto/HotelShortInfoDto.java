package com.task.hotel_service_test_task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelShortInfoDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
