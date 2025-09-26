package com.apple.hub.AppleHub.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotNull(message = "Username is required")
    private String username;

    @Email(message = "Email must be valid")
    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Min(value = 6, message = "Age must be at least 6")
    private Integer age;
    private String gender;
    private String address;
    private String coordinates;
    private String firstName;
    private String lastName;
}
