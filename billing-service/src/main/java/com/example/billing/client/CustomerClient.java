package com.example.billing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", path = "/api/customers")
public interface CustomerClient {

    @GetMapping("/{id}")
    CustomerResponse findById(@PathVariable("id") Long id);

    record CustomerResponse(Long id, String firstName, String lastName, String email) {}
}
