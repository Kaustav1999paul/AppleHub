package com.apple.hub.AppleHub.service;

import com.apple.hub.AppleHub.dto.*;
import com.apple.hub.AppleHub.entity.User;
import com.apple.hub.AppleHub.exception.BadRequestException;
import com.apple.hub.AppleHub.exception.ResourceNotFoundException;
import com.apple.hub.AppleHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


//    Register User
    public UserResponse registerUser(RegisterRequest request) {
        final Set<String> validGenders = Set.of("MALE", "FEMALE", "OTHER");

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists");
        }
        final var gender = request.getGender().toUpperCase();
        if (!validGenders.contains(gender)) {
            throw new BadRequestException("Invalid gender. Must be one of: " + validGenders);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAge(request.getAge());
        user.setGender(gender);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAddress(request.getAddress());
        user.setCoordinates(request.getCoordinates());

        userRepository.save(user);

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAge(),
                user.getGender(),
                user.getAddress(),
                user.getCoordinates(),
                user.getFirstName(),
                user.getLastName()
        );
    }



//    Login a user
    public UserResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        final String email = request.getEmail().trim();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: "+email+". Please use a valid email or create a new account."));
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAge(),
                user.getGender(),
                user.getAddress(),
                user.getCoordinates(),
                user.getFirstName(),
                user.getLastName()
        );
    }



//    Get details of the current user by ID
    public UserResponse getCurrentUserDetails(UUID uuid) {
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAge(),
                user.getGender(),
                user.getAddress(),
                user.getCoordinates(),
                user.getFirstName(),
                user.getLastName()
        );
    }



    public UserResponse updateUserDetails(UpdateUserProfileRequest request) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPassword() != null) user.setPassword(request.getPassword());
        if (request.getAge() != null) user.setAge(request.getAge());
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getAddress() != null) user.setAddress(request.getAddress());
        if (request.getCoordinates() != null) user.setCoordinates(request.getCoordinates());
        userRepository.save(user);

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAge(),
                user.getGender(),
                user.getAddress(),
                user.getCoordinates(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
