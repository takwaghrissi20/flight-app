package com.example.springboot_backend.exceptions;

public class VolNotFoundException extends RuntimeException {
    public VolNotFoundException(String message) {
        super(message);
    }
}
