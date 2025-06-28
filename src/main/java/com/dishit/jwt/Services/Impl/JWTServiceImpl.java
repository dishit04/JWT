package com.dishit.jwt.Services.Impl;

import com.dishit.jwt.Services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {

    private static final Logger logger = LoggerFactory.getLogger(JWTServiceImpl.class);

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration.minutes}")
    private long expirationMinutes;

    // Generate JWT Token
    public String generateToken(UserDetails userDetails) {
        logger.info("Generating token for user: {}", userDetails.getUsername());
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // Username as subject
                .setIssuedAt(new Date(System.currentTimeMillis())) // Current time
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expirationMinutes))) //in milliseconds
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Sign with secret key and HS256
                .compact(); // Build the JWT
    }

    //Refresh Token
    public String generateRefreshToken(Map<String,Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername()) // Username as subject
                .setIssuedAt(new Date(System.currentTimeMillis())) // Current time
                .setExpiration(new Date(System.currentTimeMillis() + 604800000)) //in milliseconds
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Sign with secret key and HS256
                .compact(); // Build the JWT
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    // Generic method to extract any claim from the JWT using a resolver function.
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (io.jsonwebtoken.JwtException e) {
            logger.error("Invalid or expired token: {}", e.getMessage());
            throw new RuntimeException("Invalid token", e);
        }
    }

    public String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }



}
