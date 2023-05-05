package com.example.project.controller;

import com.example.project.IsDayOffService;
import com.example.project.exceptions.ErrorMessage;
import com.example.project.exceptions.MeetingDayIsNotWorkDayException;
import com.example.project.exceptions.ValidateMeetingException;
import com.example.project.mapper.MeetingMapper;
import com.example.project.exceptions.MeetingNotFoundException;
import com.example.project.dao.MeetingDAO;
import com.example.project.dto.MeetingDTO;
import com.example.project.entity.Meeting;
import feign.FeignException;
import io.micrometer.core.instrument.config.validate.ValidationException;
import org.apache.http.client.protocol.ResponseContentEncoding;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Objects;
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

        try {
            ValidateMeeting(meeting);
            CheckMeetingDate(meeting);
            meetingDAO.save(meeting);
            return ResponseEntity.created(URI.create("/" + meeting.getId())).body(meeting);

        }
        
        catch (ValidateMeetingException e) {
            new ErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().build();

        } catch (MeetingDayIsNotWorkDayException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(meeting);
        }
    }

    public void ValidateMeeting(Meeting meeting) throws ValidateMeetingException {
        if (meeting.getStartDate().isAfter(meeting.getEndDate()))
            throw new ValidateMeetingException("Meeting start day is after end day");
    }

    public void CheckMeetingDate(Meeting meeting) throws MeetingDayIsNotWorkDayException {

        ResponseEntity<String> entity = isDayOffService.isDayOff(meeting.getStartDate().toLocalDate());

        if (Objects.equals(entity.getBody(), "1"))
            throw new MeetingDayIsNotWorkDayException("Meeting date is not work day");
    }

    @ExceptionHandler(ValidateMeetingException.class)
    public ResponseEntity<ErrorMessage> handleException(@NotNull ValidateMeetingException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
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
