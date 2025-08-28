package org.example.users.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
