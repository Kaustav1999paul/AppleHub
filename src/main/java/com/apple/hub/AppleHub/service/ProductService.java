package com.apple.hub.AppleHub.service;

import com.apple.hub.AppleHub.dto.ProductResponse;
import com.apple.hub.AppleHub.dto.UploadProductRequest;
import com.apple.hub.AppleHub.entity.Product;
import com.apple.hub.AppleHub.exception.BadRequestException;
import com.apple.hub.AppleHub.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


//    Upload product details
    public ProductResponse uploadProduct(UploadProductRequest request) {
        final Set<String> validCategory = Set.of("iPhones", "Macs", "AppleWatch", "Accessories", "iPad", "AirPods", "HomePods", "GiftCards");
        final var category = request.getCategory().trim();
        if (!validCategory.contains(category)) {
            throw new BadRequestException("Invalid category");
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(BigDecimal.valueOf(request.getPrice()));
        product.setOfferPrice(BigDecimal.valueOf(request.getOfferPrice()));
        product.setStock(request.getStock());
        product.setCategory(category);
        product.setImageUrl(request.getImageUrl());
        product.setRefurbished(request.isRefurbished());
        productRepository.save(product);

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice().doubleValue(),
                product.getOfferPrice().doubleValue(),
                product.getStock(),
                product.getCategory(),
                product.getImageUrl(),
                product.isRefurbished()
        );
    }

    public List<ProductResponse> getAllProducts(String category, UUID id, Boolean refurbished) {
        List<Product> products = productRepository.findAll().stream()
                .filter(p -> (category == null || p.getCategory().equals(category)))
                .filter(p -> (id == null || p.getId().equals(id)))
                .filter(p -> (refurbished == null || p.isRefurbished() == refurbished))
                .toList();

        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice().doubleValue(),
                        product.getOfferPrice().doubleValue(),
                        product.getStock(),
                        product.getCategory(),
                        product.getImageUrl(),
                        product.isRefurbished()
                ))
                .collect(Collectors.toList());
    }

}
