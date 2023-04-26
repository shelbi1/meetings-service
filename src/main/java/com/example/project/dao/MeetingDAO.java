package com.example.project.dao;

import com.example.project.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;


@Repository
public interface MeetingDAO extends JpaRepository<Meeting, UUID> {

    public UUID findByCreatedById(UUID id);

    //public void saveDTO();


}

