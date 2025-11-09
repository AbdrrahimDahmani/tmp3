package com.example.billing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service", path = "/api/products")
public interface ProductClient {

    @GetMapping("/{id}")
    ProductResponse findById(@PathVariable("id") Long id);

    record ProductResponse(Long id, String name, String description, double price, int quantity) {}
}
