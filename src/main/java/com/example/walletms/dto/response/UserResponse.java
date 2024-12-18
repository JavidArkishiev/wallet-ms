package com.example.walletms.dto.response;

public record UserResponse(
        String userId,
        String lastName,
        String firstName,
        String email,
        String phoneNumber

) {
}
