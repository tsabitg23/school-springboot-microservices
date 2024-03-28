package com.tsabitschool.pensiservice.repository;

import com.tsabitschool.pensiservice.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
