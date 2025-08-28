package org.example.users.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DtoPojoTest {

    @Test
    void phoneRequest_getters_setters() {
        PhoneRequest p = new PhoneRequest();
        p.setNumber("123");
        p.setCitycode("1");
        p.setCountrycode("57");
        assertThat(p.getNumber()).isEqualTo("123");
        assertThat(p.getCitycode()).isEqualTo("1");
        assertThat(p.getCountrycode()).isEqualTo("57");
    }

    @Test
    void userRequest_getters_setters() {
        UserRequest u = new UserRequest();
        u.setName("Juan");
        u.setEmail("juan@dominio.cl");
        u.setPassword("Aa1234");
        assertThat(u.getName()).isEqualTo("Juan");
        assertThat(u.getEmail()).isEqualTo("juan@dominio.cl");
        assertThat(u.getPassword()).isEqualTo("Aa1234");
    }

    @Test
    void responses_constructores_y_getters() {
        PhoneResponse pr = new PhoneResponse("123","1","57");
        LocalDateTime now = LocalDateTime.now();
        UserResponse ur = new UserResponse(
                "id", now, now, now, "tok", true, "Juan", "juan@dominio.cl", List.of(pr)
        );
        assertThat(ur.getId()).isEqualTo("id");
        assertThat(ur.getToken()).isEqualTo("tok");
        assertThat(ur.isActive()).isTrue();
        assertThat(ur.getEmail()).isEqualTo("juan@dominio.cl");
        assertThat(ur.getPhones()).hasSize(1);
        assertThat(ur.getPhones().get(0).getCountrycode()).isEqualTo("57");
    }
}