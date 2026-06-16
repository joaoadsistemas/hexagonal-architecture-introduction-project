package com.joao.hexagonal_architecture_introduction_project.application.ports.out;

import com.joao.hexagonal_architecture_introduction_project.application.core.domain.Customer;

public interface UpdateCustomerOutputPort {
    void update(Customer customer);
}
