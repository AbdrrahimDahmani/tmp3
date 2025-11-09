package com.example.billing.bill;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    private final BillRepository billRepository;
    private final BillMapper mapper;

    public BillController(BillRepository billRepository, BillMapper mapper) {
        this.billRepository = billRepository;
        this.mapper = mapper;
    }

    @GetMapping
    public List<BillResponse> findAll() {
        return billRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public BillResponse findById(@PathVariable Long id) {
        Bill bill = billRepository.findById(id).orElseThrow(() -> new BillNotFoundException(id));
        return mapper.toResponse(bill);
    }
}
