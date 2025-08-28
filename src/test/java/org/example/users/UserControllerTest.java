package org.example.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // limpia H2 entre tests
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void crear_usuario() throws Exception {
        String json = "{\n" +
                "  \"name\": \"Juan Rodriguez\",\n" +
                "  \"email\": \"juan@rodriguez.org\",\n" +
                "  \"password\": \"Hunter22\",\n" +
                "  \"phones\": [{ \"number\": \"1234567\", \"citycode\": \"1\", \"countrycode\": \"57\" }]\n" +
                "}";

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.isactive").value(true))
                .andExpect(jsonPath("$.email").value("juan@rodriguez.org"));
    }

    @Test
    void crear_usuario_correo_duplicado() throws Exception {
        String json = "{\n" +
                "  \"name\": \"Ana\",\n" +
                "  \"email\": \"ana@dominio.cl\",\n" +
                "  \"password\": \"Aa1234\",\n" +
                "  \"phones\": []\n" +
                "}";

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje").value("El correo ya registrado"));
    }

    @Test
    void crear_osuario_email_invalido() throws Exception {
        String json = "{\n" +
                "  \"name\": \"Juan\",\n" +
                "  \"email\": \"juan@@dominio\",\n" +
                "  \"password\": \"Aa12\",\n" +
                "  \"phones\": []\n" +
                "}";

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").exists());
    }

    @Test
    void crear_usuario_password_invalido() throws Exception {
        String json = "{\n" +
                "  \"name\": \"Juan\",\n" +
                "  \"email\": \"juan@dominio.cl\",\n" +
                "  \"password\": \"aaaaaa\",\n" +
                "  \"phones\": []\n" +
                "}";

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje")
                        .value("La contraseña debe tener 1 mayúscula, 1 minúscula y 2 dígitos"));
    }
}