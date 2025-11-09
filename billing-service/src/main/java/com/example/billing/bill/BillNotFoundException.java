package com.example.billing.bill;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BillNotFoundException extends RuntimeException {
    public BillNotFoundException(Long id) {
        super("Bill %d not found".formatted(id));
    }
}
