package com.joao.hexagonal_architecture_introduction_project.application.ports.in;

import com.joao.hexagonal_architecture_introduction_project.application.core.domain.Customer;

public interface UpdateCustomerInputPort {

    void update(Customer customer, String zipCode);

}
