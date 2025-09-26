package com.apple.hub.AppleHub.controllers;


import com.apple.hub.AppleHub.dto.OrderResponse;
import com.apple.hub.AppleHub.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Place new order
    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestParam UUID userId) {
        return ResponseEntity.ok(orderService.placeOrder(userId));
    }

    // List orders (with optional filter by orderId)
    @GetMapping
    public ResponseEntity<?> getOrders(
            @RequestParam UUID userId,
            @RequestParam(required = false) UUID orderId) {
        if (orderId != null) {
            return ResponseEntity.ok(orderService.getOrderById(userId, orderId));
        }
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    // Cancel order
    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(
            @PathVariable UUID id,
            @RequestParam UUID userId) {
        return ResponseEntity.ok(orderService.cancelOrder(userId, id));
    }
}
