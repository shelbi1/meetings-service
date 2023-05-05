package com.example.project.mapper;

import com.example.project.dto.MeetingDTO;
import com.example.project.entity.Meeting;
import org.springframework.stereotype.Service;

@Service
public class MeetingMapper {

    // entity в dto
    public MeetingDTO mapToMeetingDTO(Meeting entity) {
        MeetingDTO dto = new MeetingDTO();
        //dto.setId(entity.getId());
        dto.setName(entity.getName());
        //dto.setCreatedById(entity.getCreatedById());
        dto.setInvitedUserId(entity.getInvitedUserId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        return dto;
    }

    // dto в entity
    public Meeting mapToMeetingEntity(MeetingDTO dto) {
        Meeting entity = new Meeting();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCreatedById(dto.getCreatedById());
        entity.setInvitedUserId(dto.getInvitedUserId());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        return entity;
    }
}
