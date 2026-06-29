package com.task.hotel_service_test_task.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "hotel_amenities")
public class HotelAmenitiesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hotel_id")
    private Long hotelId;

    @Column
    private String amenity;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
    public String getAmenity() { return amenity; }
    public void setAmenity(String amenity) { this.amenity = amenity; }
}
