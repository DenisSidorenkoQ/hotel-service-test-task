package com.task.hotel_service_test_task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hotels")
public class HotelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
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
}

