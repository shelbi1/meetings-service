package com.example.project.exceptions;

public class MeetingDayIsNotWorkDayException extends RuntimeException {

    public MeetingDayIsNotWorkDayException(String message) {
        super(message);
    }
}
