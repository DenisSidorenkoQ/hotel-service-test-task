package com.task.hotel_service_test_task.dto;

public class HotelCreateRequest {
    private String name;
    private String description;
    private String brand;
    private HotelAddressDto address;
    private HotelContactsDto contacts;
    private HotelArrivalTimeDto arrivalTime;

    public static class AddressDto {
        private Integer houseNumber;
        private String street;
        private String city;
        private String country;
        private String postCode;

        public Integer getHouseNumber() { return houseNumber; }
        public void setHouseNumber(Integer houseNumber) { this.houseNumber = houseNumber; }
        public String getStreet() { return street; }
        public void setStreet(String street) { this.street = street; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public String getPostCode() { return postCode; }
        public void setPostCode(String postCode) { this.postCode = postCode; }
    }

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
}
