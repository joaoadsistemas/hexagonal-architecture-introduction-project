package com.joao.hexagonal_architecture_introduction_project.application.core.useCase;

import com.joao.hexagonal_architecture_introduction_project.application.core.domain.Address;
import com.joao.hexagonal_architecture_introduction_project.application.core.domain.Customer;
import com.joao.hexagonal_architecture_introduction_project.application.ports.in.FindCustomerByIdInputPort;
import com.joao.hexagonal_architecture_introduction_project.application.ports.in.UpdateCustomerInputPort;
import com.joao.hexagonal_architecture_introduction_project.application.ports.out.FindAddressByZipCodeOutputPort;
import com.joao.hexagonal_architecture_introduction_project.application.ports.out.UpdateCustomerOutputPort;

public class UpdateCustomerUseCase implements UpdateCustomerInputPort {

    private final FindCustomerByIdInputPort findCustomerByIdInputPort;
    private final FindAddressByZipCodeOutputPort findAddressByZipCodeOutputPort;
    private final UpdateCustomerOutputPort updateCustomerOutputPort;

    public UpdateCustomerUseCase(FindCustomerByIdInputPort findCustomerByIdInputPort,
                                 FindAddressByZipCodeOutputPort findAddressByZipCodeOutputPort,
                                 UpdateCustomerOutputPort updateCustomerOutputPort) {
        this.findCustomerByIdInputPort = findCustomerByIdInputPort;
        this.findAddressByZipCodeOutputPort = findAddressByZipCodeOutputPort;
        this.updateCustomerOutputPort = updateCustomerOutputPort;
    }

    @Override
    public void update(Customer customer, String zipCode) {
        findCustomerByIdInputPort.find(customer.getId());
        Address address = findAddressByZipCodeOutputPort.find(zipCode);
        customer.setAddress(address);
        updateCustomerOutputPort.update(customer);
    }
}
