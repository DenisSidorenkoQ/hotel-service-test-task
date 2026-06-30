package com.task.hotel_service_test_task.mapper;

import com.task.hotel_service_test_task.dto.HotelCreateRequest;
import com.task.hotel_service_test_task.dto.HotelShortInfoDto;
import com.task.hotel_service_test_task.entity.AmenitiesEntity;
import com.task.hotel_service_test_task.entity.HotelAddressEmbedded;
import com.task.hotel_service_test_task.entity.HotelContactsEmbedded;
import com.task.hotel_service_test_task.entity.HotelEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HotelMapperTest {
    private final HotelMapper hotelMapper = Mappers.getMapper(HotelMapper.class);

    @Test
    void shouldMapHotelEntityToHotelShortInfoDto() {
        HotelAddressEmbedded address = HotelAddressEmbedded.builder()
                .city("Minsk")
                .street("Lenina")
                .houseNumber(10)
                .build();
        HotelContactsEmbedded contacts = HotelContactsEmbedded.builder()
                .phone("+375291112233")
                .build();
        HotelEntity entity = HotelEntity.builder()
                .id(1L)
                .name("Grand Hotel")
                .address(address)
                .contacts(contacts)
                .amenities(List.of())
                .build();

        HotelShortInfoDto result = hotelMapper.hotelShortInfoDtoFromHotelEntity(entity);

        assertNotNull(result);
        assertEquals("10 Lenina, Minsk, null, null", result.getAddress());
        assertEquals("+375291112233", result.getPhone());
    }

    @Test
    void shouldMapHotelCreateRequestToHotelEntity() {
        HotelCreateRequest request = HotelCreateRequest.builder()
                .name("Luxury Stay")
                .build();

        HotelEntity result = hotelMapper.hotelEntityFromHotelCreateRequest(request);

        assertNotNull(result);
        assertEquals("Luxury Stay", result.getName());
    }

    @Test
    void shouldMapStringToAmenitiesEntity() {
        AmenitiesEntity result = hotelMapper.stringToAmenitiesEntity("Gym");
        AmenitiesEntity nullResult = hotelMapper.stringToAmenitiesEntity(null);

        assertNotNull(result);
        assertEquals("Gym", result.getName());
        assertNull(nullResult);
    }

    @Test
    void shouldMapStringListToAmenitiesEntityList() {
        List<String> amenitiesStrings = List.of("Bar", "Parking");

        List<AmenitiesEntity> result = hotelMapper.amenitiesEntityListFromStringList(amenitiesStrings);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Bar", result.get(0).getName());
        assertEquals("Parking", result.get(1).getName());
    }

    @Test
    void shouldHandleNullInputsGracefully() {
        assertNull(hotelMapper.hotelShortInfoDtoFromHotelEntity(null));
        assertNull(hotelMapper.hotelEntityFromHotelCreateRequest(null));
        assertNull(hotelMapper.hotelFullInfoDtoFromHotelEntity(null));
        assertNull(hotelMapper.amenitiesEntityListFromStringList(null));
    }
}
