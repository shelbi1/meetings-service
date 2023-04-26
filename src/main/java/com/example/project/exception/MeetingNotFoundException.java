package com.example.project.exception;

public class MeetingNotFoundException extends RuntimeException  {

    public MeetingNotFoundException(String message) {
        super(message);
    }
}
