package com.joao.hexagonal_architecture_introduction_project.application.ports.out;

import com.joao.hexagonal_architecture_introduction_project.application.core.domain.Address;

public interface FindAddressByZipCodeOutputPort {
    Address find(String zipCode);
}
