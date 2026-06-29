package com.task.hotel_service_test_task.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "hotel_amenities")
public class HotelAmenitiesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String amenity;
}
