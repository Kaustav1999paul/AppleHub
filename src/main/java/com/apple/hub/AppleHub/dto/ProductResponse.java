package com.apple.hub.AppleHub.dto;

import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String description,
        double price,
        double offerPrice,
        int stock,
        String category,
        String imageUrl,
        boolean refurbished
) {}
