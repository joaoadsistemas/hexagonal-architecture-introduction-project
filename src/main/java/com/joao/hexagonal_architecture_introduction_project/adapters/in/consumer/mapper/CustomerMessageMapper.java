package com.joao.hexagonal_architecture_introduction_project.adapters.in.consumer.mapper;

import com.joao.hexagonal_architecture_introduction_project.adapters.in.consumer.message.CustomerMessage;
import com.joao.hexagonal_architecture_introduction_project.application.core.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMessageMapper {

    @Mapping(target = "address", ignore = true)
    Customer toCustomer(CustomerMessage message);

}
