package com.joao.hexagonal_architecture_introduction_project.adapters.out;

import com.joao.hexagonal_architecture_introduction_project.adapters.out.repository.CustomerRepository;
import com.joao.hexagonal_architecture_introduction_project.adapters.out.repository.mapper.CustomerEntityMapper;
import com.joao.hexagonal_architecture_introduction_project.application.core.domain.Customer;
import com.joao.hexagonal_architecture_introduction_project.application.ports.out.FindCustomerByIdOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindCustomerByIdAdapter implements FindCustomerByIdOutputPort {

    private final CustomerRepository customerRepository;
    private final CustomerEntityMapper customerEntityMapper;

    @Override
    public Optional<Customer> find(String id) {
       return customerRepository.findById(id)
               .map(customerEntityMapper::toCustomer);
    }
}
