package com.joao.hexagonal_architecture_introduction_project.application.ports.out;

public interface SendCpfForValidationOutputPort {
    void send(String cpf);
}
