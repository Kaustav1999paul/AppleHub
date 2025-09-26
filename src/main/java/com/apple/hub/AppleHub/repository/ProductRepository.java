package com.apple.hub.AppleHub.repository;

import com.apple.hub.AppleHub.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository  extends JpaRepository<Product, Long> {
    Optional<Product> findById(UUID id);
    Optional<Product> findByName(String name);
    Optional<Product> findByCategory(String category);
    Optional<Product> findByRefurbished(boolean refurbished);
}
