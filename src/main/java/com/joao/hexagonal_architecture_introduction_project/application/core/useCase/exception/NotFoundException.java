package com.joao.hexagonal_architecture_introduction_project.application.core.useCase.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
