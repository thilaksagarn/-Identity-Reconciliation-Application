package com.moonrider.identity.repository;

import com.moonrider.identity.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    // Custom query method to find contacts matching either email or phone number
    List<Contact> findByEmailOrPhoneNumber(String email, String phoneNumber);
}



