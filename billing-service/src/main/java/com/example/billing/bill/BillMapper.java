package com.example.billing.bill;

import com.example.billing.client.CustomerClient;
import com.example.billing.client.ProductClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BillMapper {
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    public BillMapper(CustomerClient customerClient, ProductClient productClient) {
        this.customerClient = customerClient;
        this.productClient = productClient;
    }

    public BillResponse toResponse(Bill bill) {
        CustomerClient.CustomerResponse customer = customerClient.findById(bill.getCustomerId());
        List<BillResponse.BillLineResponse> lines = bill.getItems().stream()
                .map(item -> {
                    ProductClient.ProductResponse product = productClient.findById(item.getProductId());
                    double lineTotal = item.getPrice() * item.getQuantity();
                    return new BillResponse.BillLineResponse(
                            item.getProductId(),
                            product.name(),
                            item.getQuantity(),
                            item.getPrice(),
                            lineTotal
                    );
                })
                .toList();
        double total = lines.stream().mapToDouble(BillResponse.BillLineResponse::lineTotal).sum();
        BillResponse.CustomerDetails details = new BillResponse.CustomerDetails(
                customer.id(),
                customer.firstName(),
                customer.lastName(),
                customer.email()
        );
        return new BillResponse(
                bill.getId(),
                bill.getCustomerId(),
                bill.getCreatedAt(),
                lines,
                details,
                total
        );
    }
}
