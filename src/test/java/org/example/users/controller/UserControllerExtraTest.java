package org.example.users.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerExtraTest {
    @Autowired private MockMvc mockMvc;

    @Test
    void json_invalido_devuelve_mensaje_estandar() throws Exception {
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("NO ES JSON"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("JSON inválido"));
    }

    @Test
    void acepta_countrycode() throws Exception {
        String json = "{\n" +
                "  \"name\": \"Juan\",\n" +
                "  \"email\": \"juan@dominio.cl\",\n" +
                "  \"password\": \"Aa1234\",\n" +
                "  \"phones\": [{\"number\":\"123\",\"citycode\":\"1\",\"countrycode\":\"57\"}]\n" +
                "}";

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.phones[0].countrycode").value("57"));
    }
}