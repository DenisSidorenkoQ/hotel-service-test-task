package com.task.hotel_service_test_task.service;

import com.task.hotel_service_test_task.dto.HotelCreateRequest;
import com.task.hotel_service_test_task.dto.HotelFullInfoDto;
import com.task.hotel_service_test_task.dto.HotelShortInfoDto;
import com.task.hotel_service_test_task.entity.*;
import com.task.hotel_service_test_task.mapper.HotelMapper;
import com.task.hotel_service_test_task.repository.HotelRepository;
import com.task.hotel_service_test_task.repository.specification.HotelSpecifications;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelShortInfoDto createHotel(HotelCreateRequest request) {
        HotelEntity savedHotel = hotelRepository.save(hotelMapper.hotelEntityFromHotelCreateRequest(request));
        return hotelMapper.hotelShortInfoDtoFromHotelEntity(savedHotel);
    }

    public List<HotelShortInfoDto> getAllHotelsShort() {
        return hotelRepository.findAll().stream()
                .map(hotelMapper::hotelShortInfoDtoFromHotelEntity)
                .collect(Collectors.toList());
    }

    public Optional<HotelFullInfoDto> getHotelById(Long id) {
        Optional<HotelEntity> hotelEntityOptional = hotelRepository.findById(id);
        return hotelEntityOptional.map(hotelMapper::hotelFullInfoDtoFromHotelEntity);
    }

    public List<HotelShortInfoDto>  searchHotels(String name, String brand, String city, String country, String amenity) {
        Specification<HotelEntity> spec = Specification.allOf(
                HotelSpecifications.hasName(name),
                HotelSpecifications.hasBrand(brand),
                HotelSpecifications.hasCity(city),
                HotelSpecifications.hasCountry(country),
                HotelSpecifications.hasAmenity(amenity)
        );

        return hotelRepository.findAll(spec).stream()
                .map(hotelMapper::hotelShortInfoDtoFromHotelEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addAmenities(Long id, List<String> amenities) {
        HotelEntity hotelEntity = hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with id: " + id));

        Set<String> uniqueIncomingAmenities = new LinkedHashSet<>(amenities);

        List<AmenitiesEntity> amenitiesEntityList =
                hotelMapper.amenitiesEntityListFromStringList(new ArrayList<>(uniqueIncomingAmenities));

        boolean isChanged = false;
        for (AmenitiesEntity amenity : amenitiesEntityList) {
            if (hotelEntity.addAmenity(amenity)) {
                isChanged = true;
            }
        }

        if (isChanged) {
            hotelRepository.save(hotelEntity);
        }
    }

    @Transactional
    public Map<String, Long> getHistogram(String param) {
        List<HotelEntity> hotels = hotelRepository.findAll();

        return switch (param.toLowerCase()) {
            case HotelEntity_.BRAND -> hotels.stream()
                    .filter(h -> h.getBrand() != null)
                    .collect(Collectors.groupingBy(HotelEntity::getBrand, Collectors.counting()));
            case HotelAddressEmbedded_.CITY -> hotels.stream()
                    .filter(h -> h.getAddress() != null && h.getAddress().getCity() != null)
                    .collect(Collectors.groupingBy(h -> h.getAddress().getCity(), Collectors.counting()));
            case HotelAddressEmbedded_.COUNTRY -> hotels.stream()
                    .filter(h -> h.getAddress() != null && h.getAddress().getCountry() != null)
                    .collect(Collectors.groupingBy(h -> h.getAddress().getCountry(), Collectors.counting()));
            case HotelEntity_.AMENITIES -> hotels.stream()
                    .flatMap(h -> h.getAmenities().stream())
                    .collect(Collectors.groupingBy(AmenitiesEntity::getName, Collectors.counting()));
            default ->
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown grouping parameter: " + param);
        };
    }
}
