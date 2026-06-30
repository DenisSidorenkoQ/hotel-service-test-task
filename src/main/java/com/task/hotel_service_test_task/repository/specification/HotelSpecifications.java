package com.task.hotel_service_test_task.repository.specification;

import com.task.hotel_service_test_task.entity.AmenitiesEntity;
import com.task.hotel_service_test_task.entity.HotelEntity;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class HotelSpecifications {

    public static Specification<HotelEntity> hasName(String name) {
        return (root, query, cb) -> name == null || name.isBlank() ? null :
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<HotelEntity> hasBrand(String brand) {
        return (root, query, cb) -> brand == null || brand.isBlank() ? null :
                cb.equal(cb.lower(root.get("brand")), brand.toLowerCase());
    }

    public static Specification<HotelEntity> hasCity(String city) {
        return (root, query, cb) -> city == null || city.isBlank() ? null :
                cb.equal(cb.lower(root.get("address").get("city")), city.toLowerCase());
    }

    public static Specification<HotelEntity> hasCountry(String country) {
        return (root, query, cb) -> country == null || country.isBlank() ? null :
                cb.equal(cb.lower(root.get("address").get("country")), country.toLowerCase());
    }

    public static Specification<HotelEntity> hasAmenity(String amenity) {
        return (root, query, cb) -> {
            if (amenity == null || amenity.isBlank()) {
                return null;
            }
            Join<HotelEntity, AmenitiesEntity> amenitiesJoin = root.join("amenities");

            return cb.equal(cb.lower(amenitiesJoin.get("name")), amenity.toLowerCase());
        };
    }
}
