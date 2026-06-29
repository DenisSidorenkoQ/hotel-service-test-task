package com.task.hotel_service_test_task.mapper;

import com.task.hotel_service_test_task.dto.*;
import com.task.hotel_service_test_task.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HotelMapper {
    public HotelShortInfoDto hotelShortInfoDtoFromHotelEntity(HotelEntity entity) {
        String address = entity.getAddress() != null ? entity.getAddress().toString() : "";
        String phone = entity.getContacts() != null ? entity.getContacts().getPhone() : "";
        return new HotelShortInfoDto(entity.getId(), entity.getName(), entity.getDescription(), address, phone);
    }

    public HotelEntity hotelEntityFromHotelCreateRequest(HotelCreateRequest request) {
        HotelEntity entity = new HotelEntity();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setBrand(request.getBrand());

        if (request.getAddress() != null) {
            HotelAddressDto addressDto = request.getAddress();
            HotelAddressEmbedded hotelAddress = new HotelAddressEmbedded(addressDto.getHouseNumber(),
                    addressDto.getStreet(), addressDto.getCity(), addressDto.getCountry(), addressDto.getPostCode());
            entity.setAddress(hotelAddress);
        }

        if (request.getContacts() != null) {
            HotelContactsDto contactsDto = request.getContacts();
            HotelContactsEmbedded contacts = new HotelContactsEmbedded(contactsDto.getPhone(), contactsDto.getEmail());
            entity.setContacts(contacts);
        }

        HotelArrivalTimeEmbedded arrivalTime = new HotelArrivalTimeEmbedded();
        if (request.getArrivalTime() != null) {
            if (request.getArrivalTime().getCheckIn() != null) {
                arrivalTime.setCheckIn(request.getArrivalTime().getCheckIn());
            }
            if (request.getArrivalTime().getCheckOut() != null) {
                arrivalTime.setCheckOut(request.getArrivalTime().getCheckOut());
            }
        }
        entity.setArrivalTime(arrivalTime);
        return entity;
    }

    public HotelFullInfoDto hotelFullInfoDtoFromHotelEntity(HotelEntity entity) {
        HotelAddressDto addressDto = hotelAddressDtoFromHotelAddressEmbedded(entity.getAddress());
        HotelContactsDto contactsDto = HotelContactsDtoFromHotelContactsEmbedded(entity.getContacts());
        HotelArrivalTimeDto arrivalTimeDto = hotelArrivalTimeDtoFromHotelArrivalTimeEmbedded(entity.getArrivalTime());
        List<String> amenitiesList = entity.getAmenities().stream()
                .map(HotelAmenitiesEntity::getName)
                .toList();
        return new HotelFullInfoDto(entity.getId(), entity.getName(),
                entity.getDescription(), entity.getBrand(), addressDto, contactsDto, arrivalTimeDto, amenitiesList);
    }

    private HotelAddressDto hotelAddressDtoFromHotelAddressEmbedded(HotelAddressEmbedded hotelAddress) {
        return new HotelAddressDto(hotelAddress.getHouseNumber(), hotelAddress.getStreet(), hotelAddress.getCity(),
                hotelAddress.getCountry(), hotelAddress.getPostCode());
    }

    private HotelArrivalTimeDto hotelArrivalTimeDtoFromHotelArrivalTimeEmbedded(HotelArrivalTimeEmbedded arrivalTime) {
        return new HotelArrivalTimeDto(arrivalTime.getCheckIn(), arrivalTime.getCheckOut());
    }

    private HotelContactsDto HotelContactsDtoFromHotelContactsEmbedded(HotelContactsEmbedded contacts) {
        return new HotelContactsDto(contacts.getPhone(), contacts.getEmail());
    }
}
