package com.example.project.service;

import com.example.project.dao.MeetingDAO;
import com.example.project.dto.MeetingDTO;
import com.example.project.entity.Meeting;
import com.example.project.mapper.MeetingMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MeetingService {

    private MeetingDAO meetingDAO;
    private MeetingMapper meetingMapper;

    public List<MeetingDTO> findAll() {
        return meetingDAO.findAll().stream()
                .map(meetingMapper::mapToMeetingDTO)
                .collect(Collectors.toList());
    }

    public MeetingDTO findById(UUID id) {
        return meetingMapper.mapToMeetingDTO( //в метод mapToMeetingDTO
                meetingDAO.findById(id) //поместили результат поиска по id
                        .orElse(new Meeting()) //если ни чего не нашли, то вернем пустой entity
        );
    }
}
