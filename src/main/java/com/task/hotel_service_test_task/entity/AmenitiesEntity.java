package com.task.hotel_service_test_task.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "amenities")
public class AmenitiesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEntity hotel;

    @Column
    private String name;

    public AmenitiesEntity() {}

    public AmenitiesEntity(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public HotelEntity getHotel() { return hotel; }
    public void setHotel(HotelEntity hotel) { this.hotel = hotel; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
