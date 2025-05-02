package com.moonrider.identity.entity;

import com.moonrider.identity.enums.LinkPrecedence;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contacts")
public class Contact {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LinkPrecedence getLinkPrecedence() {
        return linkPrecedence;
    }

    public void setLinkPrecedence(LinkPrecedence linkPrecedence) {
        this.linkPrecedence = linkPrecedence;
    }

    public Long getLinkedId() {
        return linkedId;
    }

    public void setLinkedId(Long linkedId) {
        this.linkedId = linkedId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "link_precedence")
    private LinkPrecedence linkPrecedence;

    @Column(name = "linked_id")
    private Long linkedId; // If secondary, points to the primary contact's ID

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

  
}
