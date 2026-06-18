package com.joao.hexagonal_architecture_introduction_project.adapters.in.consumer;

import com.joao.hexagonal_architecture_introduction_project.adapters.in.consumer.mapper.CustomerMessageMapper;
import com.joao.hexagonal_architecture_introduction_project.adapters.in.consumer.message.CustomerMessage;
import com.joao.hexagonal_architecture_introduction_project.application.core.domain.Customer;
import com.joao.hexagonal_architecture_introduction_project.application.ports.in.UpdateCustomerInputPort;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReceiveValidatedCpfConsumer {

    private static final String TOPIC = "tp_cpf_validated";
    private final UpdateCustomerInputPort updateCustomerInputPort;
    private final CustomerMessageMapper customerMessageMapper;

    @KafkaListener(topics = TOPIC, groupId = "hexagonal")
    public void receive(CustomerMessage message) {
        Customer customer = customerMessageMapper.toCustomer(message);
        updateCustomerInputPort.update(customer, message.getZipCode());
    }

}
