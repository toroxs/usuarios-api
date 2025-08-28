package org.example.users.repository;

import org.example.users.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@DataJpaTest
class UserRepositoryTest {
    @Autowired private UserRepository repo;

    @Test
    void email_unico_en_bd() {
        LocalDateTime now = LocalDateTime.now();

        UserEntity a = new UserEntity();
        a.setId(UUID.randomUUID().toString());
        a.setName("Ana");
        a.setEmail("ana@dominio.cl");
        a.setPassword("Aa1234");
        a.setToken("t1");
        a.setActive(true);
        a.setCreated(now); a.setModified(now); a.setLastLogin(now);
        repo.saveAndFlush(a);

        UserEntity b = new UserEntity();
        b.setId(UUID.randomUUID().toString());
        b.setName("Otra");
        b.setEmail("ana@dominio.cl"); // duplicado
        b.setPassword("Aa1234");
        b.setToken("t2");
        b.setActive(true);
        b.setCreated(now); b.setModified(now); b.setLastLogin(now);

        try {
            repo.save(b);
            repo.flush(); // << fuerza la violación aquí
            fail("Se esperaba violación de unicidad (email duplicado) y no se produjo");
        } catch (Exception ex) {
            Throwable root = getRootCause(ex);
            assertThat(root).isInstanceOf(SQLException.class);
            assertThat(((SQLException) root).getSQLState()).isEqualTo("23505"); // unique_violation
        }

        assertThat(repo.existsByEmail("ana@dominio.cl")).isTrue();
    }

    private static Throwable getRootCause(Throwable t) {
        Throwable r = t;
        while (r.getCause() != null && r.getCause() != r) r = r.getCause();
        return r;
    }
}