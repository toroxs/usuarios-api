package org.example.users.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret:change_me_change_me_change_me_change_me_32b_min}")
    private String secret;

    @Value("${jwt.expiration-minutes:120}")
    private int expirationMinutes;

    public String generateToken(String userId, String email) {
        long now = System.currentTimeMillis();
        long expMillis = now + (long) expirationMinutes * 60 * 1000;

        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(userId)
                .claim("email", email)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}