package com.example.walletms.dto.request;

public record UserDto(
        String email,
        String phoneNumber,
        String otp
) {
}
