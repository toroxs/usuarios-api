package org.example.users.service;

import org.example.users.dto.PhoneRequest;
import org.example.users.dto.UserRequest;
import org.example.users.dto.UserResponse;
import org.example.users.entity.UserEntity;
import org.example.users.exception.DuplicateEmailException;
import org.example.users.repository.UserRepository;
import org.example.users.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock private UserRepository userRepository;
    @Mock private JwtService jwtService;

    @InjectMocks private UserService userService;

    @BeforeEach
    void setUp() {
        when(jwtService.generateToken(any(), any())).thenReturn("fake.jwt.token");
    }

    @Test
    void register_ok_persiste_token_y_fechas() {
        when(userRepository.existsByEmail("juan@rodriguez.org")).thenReturn(false);

        when(userRepository.save(any(UserEntity.class))).thenAnswer(inv -> {
            UserEntity e = inv.getArgument(0);
            LocalDateTime now = LocalDateTime.of(2025, 1, 1, 12, 0);
            e.setCreated(now);
            e.setModified(now);
            e.setLastLogin(now);
            return e;
        });

        UserRequest req = new UserRequest();
        req.setName("Juan Rodriguez");
        req.setEmail("juan@rodriguez.org");
        req.setPassword("Hunter22");
        PhoneRequest p = new PhoneRequest();
        p.setNumber("1234567");
        p.setCitycode("1");
        p.setCountrycode("57");
        req.setPhones(Collections.singletonList(p));

        UserResponse res = userService.register(req);

        assertThat(res.getId()).isNotBlank();
        assertThat(res.getToken()).isEqualTo("fake.jwt.token");
        assertThat(res.getCreated()).isNotNull();
        assertThat(res.getModified()).isEqualTo(res.getCreated());
        assertThat(res.getLastLogin()).isEqualTo(res.getCreated());
        assertThat(res.getEmail()).isEqualTo("juan@rodriguez.org");
        assertThat(res.getPhones()).hasSize(1);
        assertThat(res.getPhones().get(0).getCountrycode()).isEqualTo("57");

        verify(userRepository).existsByEmail("juan@rodriguez.org");
        verify(userRepository).save(any(UserEntity.class));
        verify(jwtService).generateToken(anyString(), eq("juan@rodriguez.org"));
    }

    @Test
    void register_correo_duplicado_lanza_excepcion() {
        when(userRepository.existsByEmail("ana@dominio.cl")).thenReturn(true);

        UserRequest req = new UserRequest();
        req.setName("Ana");
        req.setEmail("ana@dominio.cl");
        req.setPassword("Aa1234");

        assertThatThrownBy(() -> userService.register(req))
                .isInstanceOf(DuplicateEmailException.class)
                .hasMessage("El correo ya registrado");

        verify(userRepository, never()).save(any());
        verify(jwtService, never()).generateToken(any(), any());
    }
}
