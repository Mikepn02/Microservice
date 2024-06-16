package com.mpn.ecommerce.customer.dto;

import com.mpn.ecommerce.customer.model.Address;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email,
        Address address
) {

}