package io.github.Maike2961.hexagonal.adapters.in.controller;

import io.github.Maike2961.hexagonal.adapters.in.controller.mapper.CustomerMapper;
import io.github.Maike2961.hexagonal.adapters.in.controller.request.CustomerRequest;
import io.github.Maike2961.hexagonal.adapters.in.controller.response.CustomerResponse;
import io.github.Maike2961.hexagonal.adapters.in.controller.response.PageResponse;
import io.github.Maike2961.hexagonal.application.core.domain.Customer;
import io.github.Maike2961.hexagonal.application.ports.in.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private InsertCustomerInputPort insertCustomerInputPort;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private FindCustomerByIdInputPort findCustomerByIdInputPort;

    @Autowired
    private UpdateCustomerInputPort updateCustomerInputPort;

    @Autowired
    private DeleteCustomerByIdInputPort deleteCustomerByIdInputPort;

    @Autowired
    private FindAllCustomerInputPort findAllCustomerInputPort;

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CustomerRequest customerRequest) {
        var costumerMapping = customerMapper.toCustomer(customerRequest);
        insertCustomerInputPort.insert(costumerMapping, customerRequest.getZipCode());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable final String id) {
        var customer = findCustomerByIdInputPort.find(id);
        var customerResponse = customerMapper.toCustomerResponse(customer);
        return ResponseEntity.ok().body(customerResponse);
    }

    @GetMapping
    public ResponseEntity<PageResponse<CustomerResponse>> findAll(
            @PageableDefault(size = 10)Pageable pageable
            ) {
        Page<Customer> customers = findAllCustomerInputPort.findAll(pageable);

        Page<CustomerResponse> customerResponses = customers
                .map(customerMapper::toCustomerResponse);

        PageResponse<CustomerResponse> response = PageResponse.from(customerResponses);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable final String id, @Valid @RequestBody CustomerRequest customerRequest){
        Customer customer = customerMapper.toCustomer(customerRequest);
        customer.setId(id);
        updateCustomerInputPort.update(customer, customerRequest.getZipCode());
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable final String id) {
        deleteCustomerByIdInputPort.delete(id);
        return ResponseEntity.noContent().build();
    }
}
