package com.joao.hexagonal_architecture_introduction_project.application.core.useCase;

import com.joao.hexagonal_architecture_introduction_project.application.ports.in.DeleteCustomerInputPort;
import com.joao.hexagonal_architecture_introduction_project.application.ports.out.DeleteCustomerOutputPort;

public class DeleteCustomerUseCase implements DeleteCustomerInputPort {

    private final DeleteCustomerOutputPort deleteCustomerOutputPort;

    public DeleteCustomerUseCase(DeleteCustomerOutputPort deleteCustomerOutputPort) {
        this.deleteCustomerOutputPort = deleteCustomerOutputPort;
    }

    @Override
    public void delete(String id) {
        deleteCustomerOutputPort.delete(id);
    }


}
