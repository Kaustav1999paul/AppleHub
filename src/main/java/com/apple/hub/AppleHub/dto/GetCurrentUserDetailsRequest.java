package com.apple.hub.AppleHub.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class GetCurrentUserDetailsRequest {
    @NotNull(message = "Id is required to fetch the user data.")
    private UUID id;
}
