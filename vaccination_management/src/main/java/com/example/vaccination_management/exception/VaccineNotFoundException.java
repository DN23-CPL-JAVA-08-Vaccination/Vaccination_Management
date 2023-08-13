package com.example.vaccination_management.exception;

public class VaccineNotFoundException extends Throwable {
    public VaccineNotFoundException(String message) {
        super(message);
    }
}
