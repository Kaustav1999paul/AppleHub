package com.apple.hub.AppleHub.dto;

import com.apple.hub.AppleHub.entity.OrderItem;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

// OrderItemResponse.java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {
    private UUID productId;
    private int quantity;
    private BigDecimal price;

    public static OrderItemResponse from(OrderItem item) {
        return OrderItemResponse.builder()
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build();
    }
}

