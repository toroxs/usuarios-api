package org.example.users.repository;

import org.example.users.entity.UserEntity;
import org.hibernate.exception.ConstraintViolationException;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    UserRepository repo;

    @Test
    void email_unico_en_bd() {
        var now = LocalDateTime.now();

        // Inserta email único
        var a = new UserEntity();
        a.setId(UUID.randomUUID().toString());
        a.setName("Ana");
        a.setEmail("ana@dominio.cl");
        a.setPassword("Aa1234");
        a.setToken("t1");
        a.setActive(true);
        a.setCreated(now);
        a.setModified(now);
        a.setLastLogin(now);
        repo.saveAndFlush(a);

        // Email que ya existe
        assertThat(repo.existsByEmail("ana@dominio.cl")).isTrue();

        // Inserta el duplicado
        var b = new UserEntity();
        b.setId(UUID.randomUUID().toString());
        b.setName("Otra");
        b.setEmail("ana@dominio.cl");
        b.setPassword("Aa1234");
        b.setToken("t2");
        b.setActive(true);
        b.setCreated(now);
        b.setModified(now);
        b.setLastLogin(now);

        assertThatThrownBy(() -> repo.saveAndFlush(b))
                .isInstanceOfAny(
                        DataIntegrityViolationException.class,
                        ConstraintViolationException.class,
                        JdbcSQLIntegrityConstraintViolationException.class
                )
                .hasRootCauseInstanceOf(SQLException.class);
    }
}