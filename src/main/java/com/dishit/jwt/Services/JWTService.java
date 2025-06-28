package com.dishit.jwt.Services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {

    String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    String generateRefreshToken(Map<String,Object> extraClaims, UserDetails userDetails);

    boolean isTokenExpired(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
