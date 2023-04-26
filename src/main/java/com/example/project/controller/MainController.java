package com.example.project.controller;

import com.example.project.exception.MeetingNotFoundException;
import com.example.project.dao.MeetingDAO;
import com.example.project.dto.MeetingDTO;
import com.example.project.entity.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/meetings")
public class MainController {

    @Autowired
    private MeetingDAO meetingDAO;

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
        // проверка, что день рабочий
        meetingDAO.save(meeting);
        return ResponseEntity.created(URI.create("/" + meeting.getId())).body(meeting);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Meeting> getMeeting (@PathVariable("id") UUID id) {

        Meeting meeting = meetingDAO.findById(id).orElseThrow(() -> new MeetingNotFoundException("Meeting with ID: " + id + " is not found"));
        return ResponseEntity.ok(meeting);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Meeting> editUser(@PathVariable("id") UUID id,
                                            //@RequestBody MeetingDTO meetingDTO) {
                                            @ModelAttribute("meeting") Meeting updatedMeeting) {

        Meeting meeting = meetingDAO.findById(id).orElseThrow(() -> new MeetingNotFoundException("Meeting with ID: " + id + " is not found"));

        //meetingDAO.saveDTO(meetingDTO);


        meeting.setName(updatedMeeting.getName());
        meeting.setInvitedUserId(updatedMeeting.getInvitedUserId());
        meeting.setStartDate(updatedMeeting.getStartDate());
        meeting.setEndDate(updatedMeeting.getEndDate());
        meetingDAO.save(meeting);

        return ResponseEntity.ok(meeting);
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
