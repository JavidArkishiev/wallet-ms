package com.example.walletms.service;

import java.util.List;

public interface JwtService {


    String extractUsername(String token);

    String extractUserId(String token);

    boolean isTokenValid(String token, String userName);

    boolean isTokenExpired(String token);

    String extractPhoneNumber(String token);

    List<String> getRoles(String token);
}
