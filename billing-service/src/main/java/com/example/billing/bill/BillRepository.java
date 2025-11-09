package com.example.billing.bill;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {
    @Override
    @EntityGraph(attributePaths = "items")
    List<Bill> findAll();
}
