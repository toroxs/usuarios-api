package org.example.users.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

public class UserRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El correo es obligatorio")
    @Pattern(
            //Reestriccion formato correo - aaaaaaa@dominio.cl
            regexp = "^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$",
            message = "El formato del correo es inválido"
    )
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(
            //Reestriccion de al menos 1 mayúscula, 1 minúscula y 2 dígitos
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=(?:.*\\d){2,})[A-Za-z\\d]+$",
            message = "La contraseña debe tener 1 mayúscula, 1 minúscula y 2 dígitos"
    )
    private String password;

    @Valid
    private List<PhoneRequest> phones;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public List<PhoneRequest> getPhones() { return phones; }
    public void setPhones(List<PhoneRequest> phones) { this.phones = phones; }
}
