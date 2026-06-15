package com.joao.hexagonal_architecture_introduction_project.adapters.out;

import com.joao.hexagonal_architecture_introduction_project.adapters.out.client.FindAddressByZipCodeClient;
import com.joao.hexagonal_architecture_introduction_project.adapters.out.client.mapper.AddressResponseMapper;
import com.joao.hexagonal_architecture_introduction_project.adapters.out.client.response.AddressResponse;
import com.joao.hexagonal_architecture_introduction_project.application.core.domain.Address;
import com.joao.hexagonal_architecture_introduction_project.application.ports.out.FindAddressByZipCodeOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAddressByZipCodeAdapter implements FindAddressByZipCodeOutputPort {

    private final FindAddressByZipCodeClient findAddressByZipCodeClient;
    private final AddressResponseMapper addressResponseMapper;

    @Override
    public Address find(String zipCode) {
        AddressResponse addressResponse = findAddressByZipCodeClient.find(zipCode);
        return addressResponseMapper.toAddress(addressResponse);
    }
}
