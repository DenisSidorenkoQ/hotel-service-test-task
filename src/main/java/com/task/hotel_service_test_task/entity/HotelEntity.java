package com.task.hotel_service_test_task.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels")
public class HotelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    private String brand;

    @Embedded
    private HotelAddressEmbedded address;

    @Embedded
    private HotelContactsEmbedded contacts;

    @Embedded
    private HotelArrivalTimeEmbedded arrivalTime;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "amenity")
    private List<String> amenities = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public HotelAddressEmbedded getAddress() { return address; }
    public void setAddress(HotelAddressEmbedded address) { this.address = address; }
    public HotelContactsEmbedded getContacts() { return contacts; }
    public void setContacts(HotelContactsEmbedded contacts) { this.contacts = contacts; }
    public HotelArrivalTimeEmbedded getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(HotelArrivalTimeEmbedded arrivalTime) { this.arrivalTime = arrivalTime; }
    public List<String> getAmenities() { return amenities; }
    public void setAmenities(List<String> amenities) { this.amenities = amenities; }
}

