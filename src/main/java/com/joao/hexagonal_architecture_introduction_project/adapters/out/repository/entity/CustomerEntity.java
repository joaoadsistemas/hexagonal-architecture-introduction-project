package com.joao.hexagonal_architecture_introduction_project.adapters.out.repository.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "customers")
public class CustomerEntity {
    @Id
    private String id;
    private String name;
    private AddressEntity address;
    private String cpf;
    private Boolean isCpfValid;
}
