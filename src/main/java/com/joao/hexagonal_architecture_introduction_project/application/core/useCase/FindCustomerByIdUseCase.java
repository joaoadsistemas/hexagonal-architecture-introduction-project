package com.joao.hexagonal_architecture_introduction_project.application.core.useCase;

import com.joao.hexagonal_architecture_introduction_project.application.core.domain.Customer;
import com.joao.hexagonal_architecture_introduction_project.application.core.useCase.exception.NotFoundException;
import com.joao.hexagonal_architecture_introduction_project.application.ports.in.FindCustomerByIdInputPort;
import com.joao.hexagonal_architecture_introduction_project.application.ports.out.FindCustomerByIdOutputPort;

public class FindCustomerByIdUseCase implements FindCustomerByIdInputPort {

    private final FindCustomerByIdOutputPort findCustomerByIdOutputPort;

    public FindCustomerByIdUseCase(FindCustomerByIdOutputPort findCustomerByIdOutputPort) {
        this.findCustomerByIdOutputPort = findCustomerByIdOutputPort;
    }

    @Override
    public Customer find(String id) {
        return findCustomerByIdOutputPort.find(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
    };
}
