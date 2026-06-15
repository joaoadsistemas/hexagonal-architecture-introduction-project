package com.joao.hexagonal_architecture_introduction_project.adapters.in.controller.mapper;

import com.joao.hexagonal_architecture_introduction_project.adapters.in.controller.request.CustomerRequest;
import com.joao.hexagonal_architecture_introduction_project.application.core.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "isCpfValid", ignore = true)
    Customer toCustomer(CustomerRequest customerRequest);

}
