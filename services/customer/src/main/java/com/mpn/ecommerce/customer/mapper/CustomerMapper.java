package com.mpn.ecommerce.customer.mapper;

import com.mpn.ecommerce.customer.dto.CustomerRequest;
import com.mpn.ecommerce.customer.dto.CustomerResponse;
import com.mpn.ecommerce.customer.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerMapper {
    public Customer toCustomer(CustomerRequest request) {

        if(request == null){
            return null;
        }
        return Customer.builder()
                .id(request.id())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .address(request.address())
                .build();
    }

    public CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
