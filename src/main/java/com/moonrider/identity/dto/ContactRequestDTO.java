package com.moonrider.identity.dto;

public class ContactRequestDTO {
    // Input fields from the client
    private String email;
    private String phoneNumber;

    public ContactRequestDTO() {}

    public ContactRequestDTO(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

