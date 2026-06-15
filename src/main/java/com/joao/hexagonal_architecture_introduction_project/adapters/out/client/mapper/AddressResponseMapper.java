package com.joao.hexagonal_architecture_introduction_project.adapters.out.client.mapper;

import com.joao.hexagonal_architecture_introduction_project.adapters.out.client.response.AddressResponse;
import com.joao.hexagonal_architecture_introduction_project.application.core.domain.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressResponseMapper {

    Address toAddress(AddressResponse addressResponse);

}
