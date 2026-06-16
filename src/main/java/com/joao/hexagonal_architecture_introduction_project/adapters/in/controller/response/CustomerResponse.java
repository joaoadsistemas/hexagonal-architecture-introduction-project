package com.joao.hexagonal_architecture_introduction_project.adapters.in.controller.response;

public record CustomerResponse(
        String id,
        String name,
        AddressResponse address,
        String cpf,
        Boolean isCpfValid
) {

}
