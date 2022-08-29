package com.example.order_adapter.repository;

import com.example.order_adapter.model.Pizza;
import com.example.order_adapter.model.PizzaCrust;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    Optional<Pizza> findByName(String name);
    boolean existsByName(final String name);
}
