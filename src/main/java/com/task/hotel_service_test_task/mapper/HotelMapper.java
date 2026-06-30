package com.task.hotel_service_test_task.mapper;

import com.task.hotel_service_test_task.dto.*;
import com.task.hotel_service_test_task.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HotelMapper {

    @Mapping(target = "address", source = "address", qualifiedByName = "mapAddressToString")
    @Mapping(target = "phone", source = "contacts.phone")
    HotelShortInfoDto hotelShortInfoDtoFromHotelEntity(HotelEntity entity);

    HotelEntity hotelEntityFromHotelCreateRequest(HotelCreateRequest request);

    @Mapping(target = "amenities", source = "amenities", qualifiedByName = "mapAmenitiesToNames")
    HotelFullInfoDto hotelFullInfoDtoFromHotelEntity(HotelEntity entity);

    default AmenitiesEntity stringToAmenitiesEntity(String name) {
        if (name == null) {
            return null;
        }
        return new AmenitiesEntity(name);
    }

    List<AmenitiesEntity> amenitiesEntityListFromStringList(List<String> amenities);

    @Named("mapAddressToString")
    default String mapAddressToString(HotelAddressEmbedded address) {
        return address != null ? address.toString() : "";
    }

    @Named("mapAmenitiesToNames")
    default List<String> mapAmenitiesToNames(List<AmenitiesEntity> amenities) {
        if (amenities == null) {
            return List.of();
        }
        return amenities.stream()
                .map(AmenitiesEntity::getName)
                .toList();
    }
}
