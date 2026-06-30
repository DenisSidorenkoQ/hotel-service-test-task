package com.task.hotel_service_test_task.service;

import com.task.hotel_service_test_task.dto.HotelCreateRequest;
import com.task.hotel_service_test_task.dto.HotelFullInfoDto;
import com.task.hotel_service_test_task.dto.HotelShortInfoDto;
import com.task.hotel_service_test_task.entity.AmenitiesEntity;
import com.task.hotel_service_test_task.entity.HotelAddressEmbedded;
import com.task.hotel_service_test_task.entity.HotelEntity;
import com.task.hotel_service_test_task.mapper.HotelMapper;
import com.task.hotel_service_test_task.repository.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelService hotelService;

    @Nested
    class CreateHotelTests {

        @Test
        void createHotel_Success() {
            HotelCreateRequest request = new HotelCreateRequest();
            HotelEntity entityToSave = new HotelEntity();
            HotelEntity savedEntity = new HotelEntity();
            HotelShortInfoDto expectedDto = new HotelShortInfoDto();

            when(hotelMapper.hotelEntityFromHotelCreateRequest(request)).thenReturn(entityToSave);
            when(hotelRepository.save(entityToSave)).thenReturn(savedEntity);
            when(hotelMapper.hotelShortInfoDtoFromHotelEntity(savedEntity)).thenReturn(expectedDto);

            HotelShortInfoDto result = hotelService.createHotel(request);

            assertNotNull(result);
            assertEquals(expectedDto, result);
            verify(hotelRepository, times(1)).save(entityToSave);
        }
    }

    @Nested
    class GetAllHotelsShortTests {

        @Test
        void getAllHotelsShort_ReturnsList() {
            HotelEntity hotel1 = new HotelEntity();
            HotelEntity hotel2 = new HotelEntity();
            List<HotelEntity> hotels = Arrays.asList(hotel1, hotel2);

            HotelShortInfoDto dto1 = new HotelShortInfoDto();
            HotelShortInfoDto dto2 = new HotelShortInfoDto();

            when(hotelRepository.findAll()).thenReturn(hotels);
            when(hotelMapper.hotelShortInfoDtoFromHotelEntity(hotel1)).thenReturn(dto1);
            when(hotelMapper.hotelShortInfoDtoFromHotelEntity(hotel2)).thenReturn(dto2);

            List<HotelShortInfoDto> result = hotelService.getAllHotelsShort();

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(dto1, result.get(0));
            assertEquals(dto2, result.get(1));
        }
    }

    @Nested
    class GetHotelByIdTests {

        @Test
        void getHotelById_Found() {
            Long hotelId = 1L;
            HotelEntity entity = new HotelEntity();
            HotelFullInfoDto expectedDto = new HotelFullInfoDto();

            when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(entity));
            when(hotelMapper.hotelFullInfoDtoFromHotelEntity(entity)).thenReturn(expectedDto);

            Optional<HotelFullInfoDto> result = hotelService.getHotelById(hotelId);

            assertTrue(result.isPresent());
            assertEquals(expectedDto, result.get());
        }

        @Test
        void getHotelById_NotFound() {
            Long hotelId = 1L;
            when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

            Optional<HotelFullInfoDto> result = hotelService.getHotelById(hotelId);

            assertTrue(result.isEmpty());
            verifyNoInteractions(hotelMapper);
        }
    }

    @Nested
    class SearchHotelsTests {

        @Test
        void searchHotels_ReturnsFilteredList() {
            HotelEntity entity = new HotelEntity();
            HotelShortInfoDto dto = new HotelShortInfoDto();

            when(hotelRepository.findAll(any(Specification.class))).thenReturn(Collections.singletonList(entity));
            when(hotelMapper.hotelShortInfoDtoFromHotelEntity(entity)).thenReturn(dto);

            List<HotelShortInfoDto> result = hotelService.searchHotels("Name", "Brand", "City", "Country", "WiFi");

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(dto, result.get(0));
        }
    }

    @Nested
    class AddAmenitiesTests {

        @Test
        void addAmenities_HotelNotFound_ThrowsException() {
            Long hotelId = 1L;
            List<String> amenities = List.of("WiFi");
            when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> hotelService.addAmenities(hotelId, amenities));
            verify(hotelRepository, never()).save(any());
        }

        @Test
        void addAmenities_NewAmenitiesAdded_SavesHotel() {
            Long hotelId = 1L;
            List<String> incoming = List.of("WiFi", "Parking", "WiFi");
            HotelEntity spyHotel = spy(new HotelEntity());

            AmenitiesEntity wifiEntity = new AmenitiesEntity("WiFi");
            AmenitiesEntity parkingEntity = new AmenitiesEntity("Parking");
            List<AmenitiesEntity> mappedAmenities = Arrays.asList(wifiEntity, parkingEntity);

            when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(spyHotel));
            when(hotelMapper.amenitiesEntityListFromStringList(any(ArrayList.class))).thenReturn(mappedAmenities);

            when(spyHotel.addAmenity(wifiEntity)).thenReturn(true);
            when(spyHotel.addAmenity(parkingEntity)).thenReturn(true);

            hotelService.addAmenities(hotelId, incoming);

            verify(spyHotel).addAmenity(wifiEntity);
            verify(spyHotel).addAmenity(parkingEntity);
            verify(hotelRepository, times(1)).save(spyHotel);
        }

        @Test
        void addAmenities_NoNewAmenities_DoesNotSaveHotel() {
            Long hotelId = 1L;
            List<String> incoming = List.of("WiFi");
            HotelEntity spyHotel = spy(new HotelEntity());
            AmenitiesEntity wifiEntity = new AmenitiesEntity("WiFi");

            when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(spyHotel));
            when(hotelMapper.amenitiesEntityListFromStringList(any(ArrayList.class))).thenReturn(List.of(wifiEntity));
            when(spyHotel.addAmenity(wifiEntity)).thenReturn(false);

            hotelService.addAmenities(hotelId, incoming);

            verify(hotelRepository, never()).save(any());
        }
    }

    @Nested
    class GetHistogramTests {

        private List<HotelEntity> createMockHotels() {
            HotelEntity h1 = new HotelEntity();
            h1.setBrand("Hilton");
            HotelAddressEmbedded addr1 = HotelAddressEmbedded.builder()
                    .city("Minsk")
                    .country("Belarus")
                    .build();
            h1.setAddress(addr1);
            AmenitiesEntity a1 = new AmenitiesEntity("WiFi");
            h1.setAmenities(List.of(a1));

            HotelEntity h2 = new HotelEntity();
            h2.setBrand("Hilton");
            HotelAddressEmbedded addr2 = HotelAddressEmbedded.builder()
                    .city("Minsk")
                    .country("Belarus")
                    .build();
            h2.setAddress(addr2);
            AmenitiesEntity a2 = new AmenitiesEntity("Pool");
            h2.setAmenities(List.of(a1, a2));

            HotelEntity h3 = new HotelEntity();
            h3.setBrand("Marriott");
            HotelAddressEmbedded addr3 = HotelAddressEmbedded.builder()
                    .city("Brest")
                    .country("Belarus")
                    .build();
            h3.setAddress(addr3);
            h3.setAmenities(Collections.emptyList());

            return Arrays.asList(h1, h2, h3);
        }

        @Test
        void getHistogram_ByBrand() {
            when(hotelRepository.findAll()).thenReturn(createMockHotels());

            Map<String, Long> result = hotelService.getHistogram("brand");

            assertEquals(2, result.size());
            assertEquals(2L, result.get("Hilton"));
            assertEquals(1L, result.get("Marriott"));
        }

        @Test
        void getHistogram_ByCity() {
            when(hotelRepository.findAll()).thenReturn(createMockHotels());

            Map<String, Long> result = hotelService.getHistogram("city");

            assertEquals(2, result.size());
            assertEquals(2L, result.get("Minsk"));
            assertEquals(1L, result.get("Brest"));
        }

        @Test
        void getHistogram_ByCountry() {
            when(hotelRepository.findAll()).thenReturn(createMockHotels());

            Map<String, Long> result = hotelService.getHistogram("country");

            assertEquals(1, result.size());
            assertEquals(3L, result.get("Belarus"));
        }

        @Test
        void getHistogram_ByAmenities() {
            when(hotelRepository.findAll()).thenReturn(createMockHotels());
            Map<String, Long> result = hotelService.getHistogram("amenities");
            assertEquals(2, result.size());
            assertEquals(2L, result.get("WiFi"));
            assertEquals(1L, result.get("Pool"));
        }

        @Test
        void getHistogram_UnknownParam_ThrowsException() {
            when(hotelRepository.findAll()).thenReturn(Collections.emptyList());
            ResponseStatusException exception = assertThrows(ResponseStatusException.class,() -> hotelService.getHistogram("invalid_param"));
            assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());assertTrue(exception.getReason().contains("Unknown grouping parameter"));
        }
    }
}
