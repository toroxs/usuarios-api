package org.example.users.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

class EntityPojoTest {
    @Test
    void phoneEntity_pojo() {
        PhoneEntity p = new PhoneEntity();
        p.setId(1L);
        p.setNumber("123");
        p.setCitycode("1");
        p.setCountrycode("57");
        assertThat(p.getId()).isEqualTo(1L);
        assertThat(p.getNumber()).isEqualTo("123");
        assertThat(p.getCitycode()).isEqualTo("1");
        assertThat(p.getCountrycode()).isEqualTo("57");
    }

    @Test
    void userEntity_pojo() {
        UserEntity u = new UserEntity();
        u.setId("u1");
        u.setName("Juan");
        u.setEmail("juan@dominio.cl");
        u.setPassword("Aa1234");
        u.setToken("tok");
        u.setActive(true);
        LocalDateTime now = LocalDateTime.now();
        u.setCreated(now); u.setModified(now); u.setLastLogin(now);
        assertThat(u.getId()).isEqualTo("u1");
        assertThat(u.isActive()).isTrue();
        assertThat(u.getCreated()).isEqualTo(now);
    }
}