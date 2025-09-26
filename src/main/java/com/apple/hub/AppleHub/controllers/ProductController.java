package com.apple.hub.AppleHub.controllers;

import com.apple.hub.AppleHub.dto.ProductResponse;
import com.apple.hub.AppleHub.dto.UploadProductRequest;
import com.apple.hub.AppleHub.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    Upload product details
    @PostMapping("/upload-product")
    public ResponseEntity<ProductResponse> uploadProduct(@Valid @RequestBody UploadProductRequest request) {
        ProductResponse response = productService.uploadProduct(request);
        return ResponseEntity.ok(response);
    }

//    List all products and filter based on id, category and refurbished
    @GetMapping("/list-all-products")
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) UUID id,
            @RequestParam(required = false) Boolean refurbished) {
        List<ProductResponse> response = productService.getAllProducts(category, id, refurbished);
        return ResponseEntity.ok(response);
    }
}
