package com.moonrider.identity.service;

import com.moonrider.identity.dto.ContactRequestDTO;
import com.moonrider.identity.dto.ContactResponseDTO;
import com.moonrider.identity.entity.Contact;
import com.moonrider.identity.enums.LinkPrecedence;
import com.moonrider.identity.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moonrider.identity.enums.LinkPrecedence;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IdentityService {

    @Autowired
    private ContactRepository contactRepository;

    public ContactResponseDTO identifyContact(ContactRequestDTO request) {
        String email = request.getEmail();
        String phoneNumber = request.getPhoneNumber();

        // Fetch existing contacts with matching email or phone number
        List<Contact> contacts = contactRepository.findByEmailOrPhoneNumber(email, phoneNumber);

        if (contacts.isEmpty()) {
            // Create primary contact if no matches
            Contact primaryContact = new Contact();
            primaryContact.setEmail(email);
            primaryContact.setPhoneNumber(phoneNumber);
            primaryContact.setLinkPrecedence(LinkPrecedence.PRIMARY);
            primaryContact.setCreatedAt(LocalDateTime.now());
            primaryContact.setUpdatedAt(LocalDateTime.now());
            contactRepository.save(primaryContact);

            return new ContactResponseDTO(
                    primaryContact.getId(),
                    Collections.singletonList(email),
                    Collections.singletonList(phoneNumber),
                    Collections.emptyList()
            );
        } else {
            // Find the oldest primary contact
            Contact primary = contacts.stream()
                    .filter(c -> c.getLinkPrecedence() == LinkPrecedence.PRIMARY)
                    .min(Comparator.comparing(Contact::getCreatedAt))
                    .orElseGet(() -> contacts.get(0));

            // Create secondary contact if it's a new combination
            boolean alreadyExists = contacts.stream()
                    .anyMatch(c -> Objects.equals(c.getEmail(), email) && Objects.equals(c.getPhoneNumber(), phoneNumber));

            if (!alreadyExists) {
                // Create new secondary contact linked to primary
                Contact secondaryContact = new Contact();
                secondaryContact.setEmail(email);
                secondaryContact.setPhoneNumber(phoneNumber);
                secondaryContact.setLinkPrecedence(LinkPrecedence.SECONDARY);
                secondaryContact.setLinkedId(primary.getId());
                secondaryContact.setCreatedAt(LocalDateTime.now());
                secondaryContact.setUpdatedAt(LocalDateTime.now());
                contactRepository.save(secondaryContact);
                contacts.add(secondaryContact);
            }

            // Collect emails, phones, and secondary ids
            Set<String> emails = contacts.stream()
                    .map(Contact::getEmail)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            Set<String> phoneNumbers = contacts.stream()
                    .map(Contact::getPhoneNumber)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            List<Long> secondaryIds = contacts.stream()
                    .filter(c -> c.getLinkPrecedence() == LinkPrecedence.SECONDARY)
                    .map(Contact::getId)
                    .collect(Collectors.toList());

            return new ContactResponseDTO(
                    primary.getId(),
                    new ArrayList<>(emails),
                    new ArrayList<>(phoneNumbers),
                    secondaryIds
            );
        }
    }
}
