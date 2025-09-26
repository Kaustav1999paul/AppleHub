package com.apple.hub.AppleHub.repository;

import com.apple.hub.AppleHub.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(UUID userId);
    Optional<Order> findByIdAndUserId(UUID id, UUID userId);
}

