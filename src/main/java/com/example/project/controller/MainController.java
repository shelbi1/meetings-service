package com.example.project.controller;

import com.example.project.IsDayOffService;
import com.example.project.exceptions.ErrorMessage;
import com.example.project.exceptions.MeetingDayIsNotWorkDayException;
import com.example.project.mapper.MeetingMapper;
import com.example.project.exceptions.MeetingNotFoundException;
import com.example.project.dao.MeetingDAO;
import com.example.project.dto.MeetingDTO;
import com.example.project.entity.Meeting;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.UUID;


@RestController
@RequestMapping("/meetings")
public class MainController {

    @Autowired
    private MeetingDAO meetingDAO;

    @Autowired
    private IsDayOffService isDayOffService;

    @ResponseBody
    @RequestMapping("/")
    public String index(){
        Iterable<Meeting> all = meetingDAO.findAll();

        StringBuilder sb = new StringBuilder();

        all.forEach(p -> sb.append(p.getName() + "<br>"));

        return sb.toString();
    }

    @PostMapping(value="/")
    public ResponseEntity<Meeting> saveMeeting(@ModelAttribute("meeting") Meeting meeting) {

        if (meeting.getStartDate().isBefore(meeting.getEndDate())) {

            if (!isDayOffService.isDayOff(meeting.getStartDate())) {
                meetingDAO.save(meeting);
                return ResponseEntity.created(URI.create("/" + meeting.getId())).body(meeting);
            }
            else {
                Exception e = new MeetingDayIsNotWorkDayException("Meeting date is not work day");
                return ResponseEntity.noContent().build();
            }
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @ExceptionHandler(MeetingDayIsNotWorkDayException.class)
    public ResponseEntity<ErrorMessage> handleException(@NotNull MeetingDayIsNotWorkDayException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Meeting> getMeeting (@PathVariable("id") UUID id) {

        Meeting meeting = meetingDAO.findById(id).orElseThrow(() -> new MeetingNotFoundException("Meeting with ID: " + id + " is not found"));
        return ResponseEntity.ok(meeting);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<MeetingDTO> editUser(@PathVariable("id") UUID id,
                                               @RequestBody MeetingDTO meetingDTO) {


        //Meeting meeting = meetingDAO.findById(id).orElseThrow(() -> new MeetingNotFoundException("Meeting with ID: " + id + " is not found"));

        if (meetingDAO.existsById(id) && meetingDTO.getStartDate().isBefore(meetingDTO.getEndDate())) {
            MeetingMapper meetingMapper = new MeetingMapper();
            Meeting meetingEntity = meetingMapper.mapToMeetingEntity(meetingDTO);
            meetingDAO.save(meetingEntity);
            return ResponseEntity.ok(meetingDTO);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Meeting> delete(@PathVariable("id") UUID id) {
        try {
            meetingDAO.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

}
