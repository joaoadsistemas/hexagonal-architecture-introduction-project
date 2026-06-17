package com.joao.hexagonal_architecture_introduction_project.config;

import com.joao.hexagonal_architecture_introduction_project.adapters.out.FindAddressByZipCodeAdapter;
import com.joao.hexagonal_architecture_introduction_project.adapters.out.UpdateCustomerAdapter;
import com.joao.hexagonal_architecture_introduction_project.application.core.useCase.FindCustomerByIdUseCase;
import com.joao.hexagonal_architecture_introduction_project.application.core.useCase.UpdateCustomerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateCustomerConfig {

    @Bean
    public UpdateCustomerUseCase updateCustomerUseCase(
            FindCustomerByIdUseCase findCustomerByIdUseCase,
            FindAddressByZipCodeAdapter findAddressByZipCodeAdapter,
            UpdateCustomerAdapter updateCustomerAdapter) {
        return new UpdateCustomerUseCase(findCustomerByIdUseCase, findAddressByZipCodeAdapter, updateCustomerAdapter);
    }
}
