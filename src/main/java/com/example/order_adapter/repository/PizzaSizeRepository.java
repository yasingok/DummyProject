package com.example.order_adapter.repository;

import com.example.order_adapter.model.Pizza;
import com.example.order_adapter.model.PizzaCrust;
import com.example.order_adapter.model.PizzaSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PizzaSizeRepository extends JpaRepository<PizzaSize, Long> {
    boolean existsBySize(final String size);
    Optional<PizzaSize> findBySize(String size);
}
