package com.apple.hub.AppleHub.service;


import com.apple.hub.AppleHub.dto.OrderResponse;
import com.apple.hub.AppleHub.entity.*;
import com.apple.hub.AppleHub.repository.CartRepository;
import com.apple.hub.AppleHub.repository.OrderRepository;
import com.apple.hub.AppleHub.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse placeOrder(UUID userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        BigDecimal total = BigDecimal.ZERO;
        Order order = Order.builder()
                .userId(userId)
                .status("PENDING")
                .totalAmount(BigDecimal.ZERO)
                .build();
        for (CartItem ci : cart.getItems()) {
            Product product = productRepository.findById(ci.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity()));
            total = total.add(itemTotal);
            OrderItem oi = OrderItem.builder()
                    .order(order)
                    .productId(ci.getProductId())
                    .quantity(ci.getQuantity())
                    .price(product.getPrice())
                    .build();
            order.getItems().add(oi);
        }
        order.setTotalAmount(total);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);
        cartRepository.delete(cart);

        return OrderResponse.from(savedOrder);
    }


    public List<OrderResponse> getOrdersByUser(UUID userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderResponse::from)
                .toList();
    }

    public OrderResponse getOrderById(UUID userId, UUID orderId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return OrderResponse.from(order);
    }

    @Transactional
    public OrderResponse cancelOrder(UUID userId, UUID orderId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getStatus().equals("PENDING")) {
            throw new RuntimeException("Only PENDING orders can be canceled");
        }

        order.setStatus("CANCELED");
        return OrderResponse.from(orderRepository.save(order));
    }
}
