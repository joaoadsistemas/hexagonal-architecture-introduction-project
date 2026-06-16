package com.joao.hexagonal_architecture_introduction_project.adapters.out;

import com.joao.hexagonal_architecture_introduction_project.adapters.out.repository.CustomerRepository;
import com.joao.hexagonal_architecture_introduction_project.adapters.out.repository.mapper.CustomerEntityMapper;
import com.joao.hexagonal_architecture_introduction_project.application.core.domain.Customer;
import com.joao.hexagonal_architecture_introduction_project.application.ports.out.UpdateCustomerOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCustomerAdapter implements UpdateCustomerOutputPort {

    private final CustomerRepository customerRepository;
    private final CustomerEntityMapper customerEntityMapper;

    @Override
    public void update(Customer customer) {
        customerRepository.save(customerEntityMapper.toCustomerEntity(customer));
    }


}
