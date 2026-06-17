package com.joao.hexagonal_architecture_introduction_project.config;

import com.joao.hexagonal_architecture_introduction_project.adapters.out.DeleteCustomerAdapter;
import com.joao.hexagonal_architecture_introduction_project.application.core.useCase.DeleteCustomerUseCase;
import com.joao.hexagonal_architecture_introduction_project.application.core.useCase.UpdateCustomerUseCase;
import com.joao.hexagonal_architecture_introduction_project.application.ports.in.FindCustomerByIdInputPort;
import com.joao.hexagonal_architecture_introduction_project.application.ports.out.DeleteCustomerOutputPort;
import com.joao.hexagonal_architecture_introduction_project.application.ports.out.FindAddressByZipCodeOutputPort;
import com.joao.hexagonal_architecture_introduction_project.application.ports.out.UpdateCustomerOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeleteCustomerConfig {

    @Bean
    public DeleteCustomerUseCase deleteCustomerUseCase(
            DeleteCustomerAdapter deleteCustomerAdapter) {
        return new DeleteCustomerUseCase(deleteCustomerAdapter);
    }
}
