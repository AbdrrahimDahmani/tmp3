package com.example.billing.bill;

import com.example.billing.client.CustomerClient;
import com.example.billing.client.ProductClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BillDataInitializer {

    @Bean
    CommandLineRunner initializeBilling(BillRepository billRepository,
                                        CustomerClient customerClient,
                                        ProductClient productClient) {
        return args -> {
            if (billRepository.count() == 0) {
                CustomerClient.CustomerResponse customer = customerClient.findById(1L);
                ProductClient.ProductResponse laptop = productClient.findById(1L);
                ProductClient.ProductResponse headset = productClient.findById(2L);

                Bill bill = new Bill();
                bill.setCustomerId(customer.id());

                BillItem item1 = new BillItem();
                item1.setProductId(laptop.id());
                item1.setPrice(laptop.price());
                item1.setQuantity(1);
                item1.setBill(bill);

                BillItem item2 = new BillItem();
                item2.setProductId(headset.id());
                item2.setPrice(headset.price());
                item2.setQuantity(2);
                item2.setBill(bill);

                bill.getItems().add(item1);
                bill.getItems().add(item2);

                billRepository.save(bill);
            }
        };
    }
}
