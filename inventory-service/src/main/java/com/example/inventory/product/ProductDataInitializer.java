package com.example.inventory.product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductDataInitializer {

    @Bean
    CommandLineRunner loadProducts(ProductRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(newProduct("Laptop Pro 15", "Puissant laptop professionnel", 14999.0, 12));
                repository.save(newProduct("Casque Audio", "Casque Bluetooth à réduction de bruit", 1299.0, 35));
                repository.save(newProduct("Caméra 4K", "Caméra d'action étanche 4K", 2899.0, 18));
            }
        };
    }

    private Product newProduct(String name, String description, double price, int quantity) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        return product;
    }
}
