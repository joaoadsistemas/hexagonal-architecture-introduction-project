package com.joao.hexagonal_architecture_introduction_project.application.ports.in.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String id) {
        super("Customer with ID: " + id + " not found");
    }
}
