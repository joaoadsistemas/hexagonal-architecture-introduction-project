package com.joao.hexagonal_architecture_introduction_project.adapters.out;

import com.joao.hexagonal_architecture_introduction_project.application.ports.out.SendCpfForValidationOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendCpfValidationAdapter implements SendCpfForValidationOutputPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "tp_cpf_validation";

    @Override
    public void send(String cpf) {
        kafkaTemplate.send(TOPIC, cpf);
    }

}
