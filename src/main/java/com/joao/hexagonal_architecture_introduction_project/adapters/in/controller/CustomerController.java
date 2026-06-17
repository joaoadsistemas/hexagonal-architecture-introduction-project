package com.joao.hexagonal_architecture_introduction_project.adapters.in.controller;

import com.joao.hexagonal_architecture_introduction_project.adapters.in.controller.mapper.CustomerMapper;
import com.joao.hexagonal_architecture_introduction_project.adapters.in.controller.request.CustomerRequest;
import com.joao.hexagonal_architecture_introduction_project.adapters.in.controller.response.CustomerResponse;
import com.joao.hexagonal_architecture_introduction_project.application.core.domain.Customer;
import com.joao.hexagonal_architecture_introduction_project.application.ports.in.DeleteCustomerInputPort;
import com.joao.hexagonal_architecture_introduction_project.application.ports.in.FindCustomerByIdInputPort;
import com.joao.hexagonal_architecture_introduction_project.application.ports.in.InsertCustomerInputPort;
import com.joao.hexagonal_architecture_introduction_project.application.ports.in.UpdateCustomerInputPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerMapper customerMapper;
    private final InsertCustomerInputPort insertCustomerInputPort;
    private final FindCustomerByIdInputPort findCustomerByIdInputPort;
    private final UpdateCustomerInputPort updateCostumerInputPort;
    private final DeleteCustomerInputPort deleteCustomerInputPort;

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CustomerRequest request) {
        Customer costumer = customerMapper.toCustomer(request);
        insertCustomerInputPort.insert(costumer, request.zipCode());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> insert(@PathVariable String id) {
        return ResponseEntity.ok().body(customerMapper.toCustomerResponse(findCustomerByIdInputPort.find(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid CustomerRequest request) {
        Customer costumer = customerMapper.toCustomer(request);
        costumer.setId(id);
        updateCostumerInputPort.update(costumer, request.zipCode());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        deleteCustomerInputPort.delete(id);
        return ResponseEntity.noContent().build();
    }

}
