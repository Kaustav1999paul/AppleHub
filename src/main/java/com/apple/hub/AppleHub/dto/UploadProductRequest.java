package com.apple.hub.AppleHub.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadProductRequest {

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    private double price;
    private double offerPrice;

    @NotNull(message = "Stock is required")
    private int stock;

    @NotNull(message = "Category is required")
    private String category;

    @NotNull(message = "Image URL is required")
    private String imageUrl;

    @NotNull(message = "Refurbished flag is required")
    private boolean refurbished;
}
