package com.example.project.exceptions;

public class MeetingNotFoundException extends RuntimeException  {

    public MeetingNotFoundException(String message) {
        super(message);
    }
}
