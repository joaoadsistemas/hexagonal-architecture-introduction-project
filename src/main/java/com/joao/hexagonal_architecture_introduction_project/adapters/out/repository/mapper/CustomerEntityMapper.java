package com.joao.hexagonal_architecture_introduction_project.adapters.out.repository.mapper;

import com.joao.hexagonal_architecture_introduction_project.adapters.out.repository.entity.CustomerEntity;
import com.joao.hexagonal_architecture_introduction_project.application.core.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerEntityMapper {

    CustomerEntity toCustomerEntity(Customer customer);

}
