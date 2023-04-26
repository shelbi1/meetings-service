package com.example.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;
import java.time.LocalDate;

@Entity
@Table(name = "meetings")
public class Meeting {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "created_by_id", nullable = false)
    private UUID createdById;

    @Column(name = "invited_user_id")
    private UUID invitedUserId = null;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getCreatedById() {
        return createdById;
    }

    public void setCreatedById(UUID createdById){
        this.createdById = createdById;
    }

    public UUID getInvitedUserId() {
        return invitedUserId;
    }

    public void setInvitedUserId(UUID invitedUserId){
        this.invitedUserId = invitedUserId;
    }

    public LocalDateTime getStartDate(){
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate){
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate(){
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate){
        this.endDate = endDate;
    }
}
