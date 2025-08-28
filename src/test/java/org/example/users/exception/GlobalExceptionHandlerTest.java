package org.example.users.exception;

import org.example.users.dto.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler h = new GlobalExceptionHandler();

    @Test
    void handleDuplicate() {
        var resp = h.handleDuplicate(new DuplicateEmailException("El correo ya registrado"));
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void handleConstraint() {
        var resp = h.handleConstraint(new DataIntegrityViolationException("x"));
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void handleGeneric() {
        var resp = h.handleGeneric(new RuntimeException("boom"));
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void handleHttpMessageNotReadable() {
        var resp = h.handleHttpMessageNotReadable(
                new HttpMessageNotReadableException("bad"),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                new ServletWebRequest(new MockHttpServletRequest())
        );
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleMethodArgumentNotValid() throws Exception {
        UserRequest target = new UserRequest();
        BeanPropertyBindingResult br = new BeanPropertyBindingResult(target, "userRequest");
        br.addError(new FieldError("userRequest", "email", "El formato del correo es inválido"));

        MethodParameter mp = new MethodParameter(
                Dummy.class.getDeclaredMethod("stub", UserRequest.class), 0);

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(mp, br);

        var resp = h.handleMethodArgumentNotValid(
                ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, new ServletWebRequest(new MockHttpServletRequest()));

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    static class Dummy { void stub(UserRequest r) {} }
}