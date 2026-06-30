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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private List<AmenitiesEntity> amenities = new ArrayList<>();

    public boolean addAmenity(AmenitiesEntity amenity) {
        if (this.amenities == null) {
            this.amenities = new ArrayList<>();
        }

        boolean alreadyExists = this.amenities.stream()
                .anyMatch(a -> a.getName().equalsIgnoreCase(amenity.getName()));

        if (!alreadyExists) {
            this.amenities.add(amenity);
            amenity.setHotel(this);
            return true;
        }
        return false;
    }

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
    public List<AmenitiesEntity> getAmenities() { return amenities; }
    public void setAmenities(List<AmenitiesEntity> amenities) { this.amenities = amenities; }
}

