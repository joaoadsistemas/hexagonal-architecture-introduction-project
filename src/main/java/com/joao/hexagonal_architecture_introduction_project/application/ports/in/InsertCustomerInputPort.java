package com.joao.hexagonal_architecture_introduction_project.application.ports.in;

import com.joao.hexagonal_architecture_introduction_project.application.core.domain.Customer;

public interface InsertCustomerInputPort {

    void insert(Customer customer, String zipCode);

}
