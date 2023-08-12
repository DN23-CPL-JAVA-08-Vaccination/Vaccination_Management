package com.example.vaccination_management.exception;

public class InventoryNotFoundException extends Throwable{
    public InventoryNotFoundException(String message) {
        super(message);
    }
}
