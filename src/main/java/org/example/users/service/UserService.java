package org.example.users.service;

import org.example.users.dto.*;
import org.example.users.entity.PhoneEntity;
import org.example.users.entity.UserEntity;
import org.example.users.exception.DuplicateEmailException;
import org.example.users.repository.UserRepository;
import org.example.users.security.JwtService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserResponse register(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("El correo ya registrado");
        }

        UserEntity entity = new UserEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
        entity.setPassword(request.getPassword()); // Nota: en producción se debe hashear.
        entity.setActive(true);

        String token = jwtService.generateToken(entity.getId(), entity.getEmail());
        entity.setToken(token);


        if (request.getPhones() != null) {
            for (PhoneRequest p : request.getPhones()) {
                PhoneEntity pe = new PhoneEntity();
                pe.setNumber(p.getNumber());
                pe.setCitycode(p.getCitycode());
                pe.setCountrycode(p.getCountrycode());
                pe.setUser(entity);
                entity.getPhones().add(pe);
            }
        }
        UserEntity saved = userRepository.save(entity);

        return toResponse(saved);
    }


    private UserResponse toResponse(UserEntity u) {
        List<PhoneResponse> phones = u.getPhones().stream()
                .map(p -> new PhoneResponse(p.getNumber(), p.getCitycode(), p.getCountrycode()))
                .collect(Collectors.toList());

        return new UserResponse(
                u.getId(),
                u.getCreated(),
                u.getModified(),
                u.getLastLogin(),
                u.getToken(),
                u.isActive(),
                u.getName(),
                u.getEmail(),
                phones
        );
    }
}