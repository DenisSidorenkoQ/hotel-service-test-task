package com.task.hotel_service_test_task.service;

import com.task.hotel_service_test_task.dto.HotelCreateRequest;
import com.task.hotel_service_test_task.dto.HotelFullInfoDto;
import com.task.hotel_service_test_task.dto.HotelShortInfoDto;
import com.task.hotel_service_test_task.entity.AmenitiesEntity;
import com.task.hotel_service_test_task.entity.HotelEntity;
import com.task.hotel_service_test_task.mapper.HotelMapper;
import com.task.hotel_service_test_task.repository.HotelRepository;
import com.task.hotel_service_test_task.repository.specification.HotelSpecifications;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelService(@Autowired HotelRepository hotelRepository, @Autowired HotelMapper hotelMapper) {
        this.hotelRepository = hotelRepository;
        this.hotelMapper = hotelMapper;
    }

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
}
