package com.apple.hub.AppleHub.controllers;

import com.apple.hub.AppleHub.entity.Cart;
import com.apple.hub.AppleHub.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCart(@RequestParam UUID userId) {
        return ResponseEntity.ok(cartService.getUserCart(userId));
    }

    @PostMapping("/items")
    public ResponseEntity<Cart> addItem(
            @RequestParam UUID userId,
            @RequestParam UUID productId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addItem(userId, productId, quantity));
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<Cart> updateItem(
            @RequestParam UUID userId,
            @PathVariable UUID itemId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateItem(userId, itemId, quantity));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItem(
            @RequestParam UUID userId,
            @PathVariable UUID itemId) {
        cartService.removeItem(userId, itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@RequestParam UUID userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
