package com.apple.hub.AppleHub.dto;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email,
        Integer age,
        String gender,
        String address,
        String coordinates,
        String firstName,
        String lastName
) {}
