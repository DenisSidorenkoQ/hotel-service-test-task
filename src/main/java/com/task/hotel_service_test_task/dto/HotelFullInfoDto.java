package com.task.hotel_service_test_task.dto;

import java.util.ArrayList;
import java.util.List;

public class HotelFullInfoDto {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private HotelAddressDto address;
    private HotelContactsDto contacts;
    private HotelArrivalTimeDto arrivalTime;
    private List<String> amenities = new ArrayList<>();

    public HotelFullInfoDto(Long id, String name, String description, String brand, HotelAddressDto address,
                            HotelContactsDto contacts, HotelArrivalTimeDto arrivalTime, List<String> amenities) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.address = address;
        this.contacts = contacts;
        this.arrivalTime = arrivalTime;
        this.amenities = amenities;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public HotelAddressDto getAddress() { return address; }
    public void setAddress(HotelAddressDto address) { this.address = address; }
    public HotelContactsDto getContacts() { return contacts; }
    public void setContacts(HotelContactsDto contacts) { this.contacts = contacts; }
    public HotelArrivalTimeDto getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(HotelArrivalTimeDto arrivalTime) { this.arrivalTime = arrivalTime; }
    public List<String> getAmenities() { return amenities; }
    public void setAmenities(List<String> amenities) { this.amenities = amenities; }
}
