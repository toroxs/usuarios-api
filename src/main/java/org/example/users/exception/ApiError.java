package org.example.users.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiError {
    @JsonProperty("mensaje")
    private final String mensaje;
    public ApiError(String mensaje) { this.mensaje = mensaje; }
    public String getMensaje() { return mensaje; }
}