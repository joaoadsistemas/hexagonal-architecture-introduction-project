package com.joao.hexagonal_architecture_introduction_project.adapters.out.repository.entity;

import lombok.Data;

@Data
public class AddressEntity {
    private String id;
    private String street;
    private String city;
    private String state;
}
