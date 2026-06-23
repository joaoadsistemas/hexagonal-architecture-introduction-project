package com.joao.hexagonal_architecture_introduction_project.adapters.in.controller.handler;

import com.joao.hexagonal_architecture_introduction_project.application.ports.in.exception.CustomerNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<StandardError> handleCustomerNotFoundException(CustomerNotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(404).body(new StandardError(
                System.currentTimeMillis(),
                404,
                e.getMessage(),
                request.getRequestURI()
        ));
    }

}
