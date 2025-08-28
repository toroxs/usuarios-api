package org.example.users.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {
    private JwtService jwtService;

    @BeforeEach
    void setUp() throws Exception {
        jwtService = new JwtService();
        setField(jwtService, "secret", "secreto_para_tests_1234567890");
        setField(jwtService, "expirationMinutes", 60);
    }

    @Test
    void generateToken_incluye_subject_y_email() {
        String token = jwtService.generateToken("user-123", "test@dominio.cl");
        Claims claims = Jwts.parser()
                .setSigningKey("secreto_para_tests_1234567890".getBytes())
                .parseClaimsJws(token)
                .getBody();

        assertThat(claims.getSubject()).isEqualTo("user-123");
        assertThat(claims.get("email", String.class)).isEqualTo("test@dominio.cl");
        assertThat(claims.getExpiration()).isNotNull();
        assertThat(claims.getIssuedAt()).isNotNull();
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }
}
