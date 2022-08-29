package com.example.order_adapter.repository;

import com.example.order_adapter.model.Pizza;
import com.example.order_adapter.model.PizzaCrust;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PizzaCrustRepository extends JpaRepository<PizzaCrust, Long> {
    boolean existsByCrust(final String crust);
    Optional<PizzaCrust> findByCrust(String crust);
}
