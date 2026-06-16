package com.joao.hexagonal_architecture_introduction_project.application.ports.out;

import com.joao.hexagonal_architecture_introduction_project.application.core.domain.Customer;

import java.util.Optional;

public interface FindCustomerByIdOutputPort {
    Optional<Customer> find(String id);
}
