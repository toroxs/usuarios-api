package org.example.users.validation;

import org.example.users.dto.UserRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserRequestValidationTest {
    private static Validator validator;

    @BeforeAll
    static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void email_invalido() {
        UserRequest u = new UserRequest();
        u.setName("Juan");
        u.setEmail("juan@@dominio");
        u.setPassword("Aa12");

        Set<ConstraintViolation<UserRequest>> v = validator.validate(u);
        assertThat(v).anySatisfy(cv -> assertThat(cv.getMessage()).contains("correo"));
    }

    @Test
    void password_invalida() {
        UserRequest u = new UserRequest();
        u.setName("Juan");
        u.setEmail("juan@dominio.cl");
        u.setPassword("aaaaaa");

        Set<ConstraintViolation<UserRequest>> v = validator.validate(u);
        assertThat(v).anySatisfy(cv -> assertThat(cv.getMessage()).contains("contraseña"));
    }
}
