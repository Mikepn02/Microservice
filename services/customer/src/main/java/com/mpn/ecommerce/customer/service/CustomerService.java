package com.mpn.ecommerce.customer.service;

import com.mpn.ecommerce.customer.dto.CustomerRequest;
import com.mpn.ecommerce.customer.dto.CustomerResponse;
import com.mpn.ecommerce.customer.exception.CustomerNotFoundException;
import com.mpn.ecommerce.customer.model.Customer;
import com.mpn.ecommerce.customer.repository.CustomerRepository;
import com.mpn.ecommerce.customer.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest request){
        var customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        var customer = repository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Can not update the customer:: No customer with the id :: %s" , request.id())));

        mergerCustomer(customer , request);
        repository.save(customer);
    }

    public void mergerCustomer(Customer customer , CustomerRequest request){
        if(StringUtils.isNotBlank(request.firstname())){
            customer.setFirstname(request.firstname());
        }
        if(StringUtils.isNotBlank(request.lastname())){
            customer.setFirstname(request.lastname());
        }

        if(StringUtils.isNotBlank(request.email())){
            customer.setEmail(request.email());
        }

        if(request.address() != null){
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return repository.findAll()
                .stream()
                .map(mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return repository.findById(customerId)
                .isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return  repository.findById(customerId)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("No customer found with the provided ID: %s", customerId)));
    }

    public void deleteCustomer(String customerId) {
        repository.deleteById(customerId);
    }
}
