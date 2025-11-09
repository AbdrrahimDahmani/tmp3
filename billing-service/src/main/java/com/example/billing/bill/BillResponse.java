package com.example.billing.bill;

import java.time.LocalDateTime;
import java.util.List;

public record BillResponse(
        Long id,
        Long customerId,
        LocalDateTime createdAt,
        List<BillLineResponse> items,
        CustomerDetails customer,
        double total
) {
    public record BillLineResponse(Long productId, String productName, int quantity, double price, double lineTotal) {}
    public record CustomerDetails(Long id, String firstName, String lastName, String email) {}
}
