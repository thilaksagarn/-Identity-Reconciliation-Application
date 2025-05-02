package com.moonrider.identity.controller;

import com.moonrider.identity.dto.ContactRequestDTO;
import com.moonrider.identity.dto.ContactResponseDTO;
import com.moonrider.identity.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class IdentityController {

    @Autowired
    private IdentityService identityService;

    @PostMapping("/identify")
    public ResponseEntity<ContactResponseDTO> identify(@RequestBody ContactRequestDTO request) {
        return ResponseEntity.ok(identityService.identifyContact(request));
    }
}
