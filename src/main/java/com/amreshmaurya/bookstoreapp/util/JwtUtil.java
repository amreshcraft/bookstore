package com.amreshmaurya.bookstoreapp.util;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.amreshmaurya.bookstoreapp.config.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {

    private SecretKey key;
    private long accessExpiry;
    private long refreshExpiry;

    JwtUtil(JwtConfig jwtConfig) {
        this.key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
        this.accessExpiry = jwtConfig.getAccessExpiration();
        this.refreshExpiry = jwtConfig.getRefreshExpiration();
    }

    public String generateToken(String username) {
        Instant now = Instant.now();
        return Jwts
                .builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(accessExpiry)))
                .signWith(key)
                .claim("type", "ACCESS")
                .compact();

    }

    public String generateRefreshToken(String username) {
        Instant now = Instant.now();
        return Jwts
                .builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(refreshExpiry)))
                .signWith(key)
                .claim("type", "REFRESH")
                .compact();

    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new RuntimeException("Invalid or expired token");
        }
    }

    public String extractUsername(String token) {
        return validateToken(token).getSubject();
    }

    public boolean isAccessToken(String token) {
        return "ACCESS".equals(validateToken(token).get("type"));
    }

    public boolean isRefreshToken(String token) {
        return "REFRESH".equals(validateToken(token).get("type"));
    }

}
