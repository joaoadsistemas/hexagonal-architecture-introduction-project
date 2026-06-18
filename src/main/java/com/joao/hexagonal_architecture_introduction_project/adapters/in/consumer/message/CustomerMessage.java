package com.joao.hexagonal_architecture_introduction_project.adapters.in.consumer.message;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerMessage {

    private String id;
    private String name;
    private String zipCode;
    private String cpf;
    private Boolean isCpfValid;

}
