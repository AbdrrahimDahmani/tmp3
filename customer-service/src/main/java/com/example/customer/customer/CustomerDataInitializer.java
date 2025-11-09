package com.example.customer.customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerDataInitializer {

    @Bean
    CommandLineRunner loadCustomers(CustomerRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(newCustomer("Imane", "Bennani", "imane.bennani@example.com"));
                repository.save(newCustomer("Yassine", "El Idrissi", "yassine.elidrissi@example.com"));
                repository.save(newCustomer("Leila", "Haddad", "leila.haddad@example.com"));
            }
        };
    }

    private Customer newCustomer(String firstName, String lastName, String email) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        return customer;
    }
}
