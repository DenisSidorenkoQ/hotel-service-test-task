package com.task.hotel_service_test_task.controller;

import com.task.hotel_service_test_task.dto.HotelCreateRequest;
import com.task.hotel_service_test_task.dto.HotelFullInfoDto;
import com.task.hotel_service_test_task.dto.HotelShortInfoDto;
import com.task.hotel_service_test_task.entity.HotelEntity;
import com.task.hotel_service_test_task.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/property-view")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(@Autowired HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping("/hotels")
    @ResponseStatus(HttpStatus.CREATED)
    public HotelShortInfoDto createHotel(@RequestBody HotelCreateRequest request) {
        return hotelService.createHotel(request);
    }

    @GetMapping("/hotels")
    public List<HotelShortInfoDto> getAllHotels() {
        return hotelService.getAllHotelsShort();
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelFullInfoDto> getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<HotelShortInfoDto> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String amenities) {
        return hotelService.searchHotels(name, brand, city, country, amenities);
    }

    @PostMapping("/hotels/{id}/amenities")
    public void addAmenities(@PathVariable Long id, @RequestBody List<String> amenities) {
        hotelService.addAmenities(id, amenities);
    }

    @GetMapping("/histogram/{param}")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return hotelService.getHistogram(param);
    }
}