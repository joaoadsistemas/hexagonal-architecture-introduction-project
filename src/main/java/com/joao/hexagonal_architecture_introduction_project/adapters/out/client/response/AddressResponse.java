package com.joao.hexagonal_architecture_introduction_project.adapters.out.client.response;

import lombok.Data;

@Data
public class AddressResponse {
    private String street;
    private String city;
    private String state;
}
