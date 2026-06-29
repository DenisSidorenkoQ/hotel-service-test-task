package com.task.hotel_service_test_task.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class HotelContactsEmbedded {
    private String phone;
    private String email;

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
