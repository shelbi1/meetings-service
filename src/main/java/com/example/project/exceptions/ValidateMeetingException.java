package com.example.project.exceptions;

import io.micrometer.core.instrument.config.validate.ValidationException;

public class ValidateMeetingException extends RuntimeException {
    public ValidateMeetingException (String message) {
        super(message);
    }
}
