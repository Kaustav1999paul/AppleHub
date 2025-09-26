package com.apple.hub.AppleHub.service;

import com.apple.hub.AppleHub.entity.Cart;
import com.apple.hub.AppleHub.entity.CartItem;
import com.apple.hub.AppleHub.repository.CartItemRepository;
import com.apple.hub.AppleHub.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Cart getUserCart(UUID userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    return cartRepository.save(newCart);
                });
    }

    public Cart addItem(UUID userId, UUID productId, int quantity) {
        Cart cart = getUserCart(userId);

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProductId(productId);
            item.setQuantity(quantity);
            cart.getItems().add(item);
        }

        return cartRepository.save(cart);
    }

    public Cart updateItem(UUID userId, UUID itemId, int quantity) {
        Cart cart = getUserCart(userId);
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        item.setQuantity(quantity);
        cartItemRepository.save(item);
        return cart;
    }

    public void removeItem(UUID userId, UUID itemId) {
        cartItemRepository.deleteById(itemId);
    }

    public void clearCart(UUID userId) {
        Cart cart = getUserCart(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
