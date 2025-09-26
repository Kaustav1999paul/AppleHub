package com.apple.hub.AppleHub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateUserProfileRequest {

    @NotNull(message = "Id is required")
    private UUID id;

    @Email(message = "Email must be in valid format")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Min(value = 6, message = "Age must be at least 6")
    private Integer age;
    private String firstName;
    private String lastName;
    private String address;
    private String coordinates;
}
