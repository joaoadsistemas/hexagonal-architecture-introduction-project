package com.joao.hexagonal_architecture_introduction_project.config;

import com.joao.hexagonal_architecture_introduction_project.application.core.useCase.UpdateCustomerUseCase;
import com.joao.hexagonal_architecture_introduction_project.application.ports.in.FindCustomerByIdInputPort;
import com.joao.hexagonal_architecture_introduction_project.application.ports.out.FindAddressByZipCodeOutputPort;
import com.joao.hexagonal_architecture_introduction_project.application.ports.out.UpdateCustomerOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateCustomerConfig {

    @Bean
    public UpdateCustomerUseCase updateCustomerUseCase(
            FindCustomerByIdInputPort findCustomerByIdInputPort,
            FindAddressByZipCodeOutputPort findAddressByZipCodeOutputPort,
            UpdateCustomerOutputPort updateCostumerOutputPort) {
        return new UpdateCustomerUseCase(findCustomerByIdInputPort, findAddressByZipCodeOutputPort, updateCostumerOutputPort);
    }
}
