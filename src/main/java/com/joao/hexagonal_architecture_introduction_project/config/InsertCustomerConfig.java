package com.joao.hexagonal_architecture_introduction_project.config;

import com.joao.hexagonal_architecture_introduction_project.adapters.out.FindAddressByZipCodeAdapter;
import com.joao.hexagonal_architecture_introduction_project.adapters.out.InsertCustomerAdapter;
import com.joao.hexagonal_architecture_introduction_project.adapters.out.SendCpfValidationAdapter;
import com.joao.hexagonal_architecture_introduction_project.application.core.useCase.InsertCustomerUseCase;
import com.joao.hexagonal_architecture_introduction_project.application.ports.out.FindAddressByZipCodeOutputPort;
import com.joao.hexagonal_architecture_introduction_project.application.ports.out.InsertCustomerOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsertCustomerConfig {

    @Bean
    public InsertCustomerUseCase insertCustomerUseCase(
            FindAddressByZipCodeAdapter findAddressByZipCodeAdapter,
            InsertCustomerAdapter insertCustomerAdapter,
            SendCpfValidationAdapter sendCpfValidationAdapter) {
        return new InsertCustomerUseCase(findAddressByZipCodeAdapter, insertCustomerAdapter, sendCpfValidationAdapter);
    }
}
