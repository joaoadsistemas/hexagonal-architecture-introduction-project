package com.joao.hexagonal_architecture_introduction_project.config;

import com.joao.hexagonal_architecture_introduction_project.adapters.out.FindCustomerByIdAdapter;
import com.joao.hexagonal_architecture_introduction_project.application.core.useCase.FindCustomerByIdUseCase;
import com.joao.hexagonal_architecture_introduction_project.application.core.useCase.InsertCustomerUseCase;
import com.joao.hexagonal_architecture_introduction_project.application.ports.out.FindAddressByZipCodeOutputPort;
import com.joao.hexagonal_architecture_introduction_project.application.ports.out.FindCustomerByIdOutputPort;
import com.joao.hexagonal_architecture_introduction_project.application.ports.out.InsertCustomerOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindByIdCustomerConfig {

    @Bean
    public FindCustomerByIdUseCase findCustomerByIdUseCase(
            FindCustomerByIdAdapter findCustomerByIdAdapter) {
        return new FindCustomerByIdUseCase(findCustomerByIdAdapter);
    }
}
