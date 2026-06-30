package com.task.hotel_service_test_task.repository.specification;

import com.task.hotel_service_test_task.entity.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class HotelSpecifications {

    public static Specification<HotelEntity> hasName(String name) {
        return (root, query, cb) -> isBlank(name) ? null :
                cb.like(cb.lower(root.get(HotelEntity_.name)), "%" + name.toLowerCase() + "%");
    }

    public static Specification<HotelEntity> hasBrand(String brand) {
        return (root, query, cb) -> isBlank(brand) ? null :
                cb.equal(cb.lower(root.get(HotelEntity_.brand)), brand.toLowerCase());
    }

    public static Specification<HotelEntity> hasCity(String city) {
        return (root, query, cb) -> isBlank(city) ? null :
                cb.equal(cb.lower(root.get(HotelEntity_.address).get(HotelAddressEmbedded_.city)), city.toLowerCase());
    }

    public static Specification<HotelEntity> hasCountry(String country) {
        return (root, query, cb) -> isBlank(country) ? null :
                cb.equal(cb.lower(root.get(HotelEntity_.address).get(HotelAddressEmbedded_.country)), country.toLowerCase());
    }

    public static Specification<HotelEntity> hasAmenity(String amenity) {
        return (root, query, cb) -> {
            if (isBlank(amenity)) {
                return null;
            }
            Join<HotelEntity, AmenitiesEntity> amenitiesJoin = root.join(HotelEntity_.amenities);
            return cb.equal(cb.lower(amenitiesJoin.get(AmenitiesEntity_.name)), amenity.toLowerCase());
        };
    }

    private static boolean isBlank(String str) {
        return str == null || str.isBlank();
    }
}