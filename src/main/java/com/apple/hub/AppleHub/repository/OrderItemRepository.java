package com.apple.hub.AppleHub.repository;


import com.apple.hub.AppleHub.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}

