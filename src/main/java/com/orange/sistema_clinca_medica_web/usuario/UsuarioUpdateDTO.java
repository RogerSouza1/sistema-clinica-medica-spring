package com.orange.sistema_clinca_medica_web.usuario;

import com.orange.sistema_clinca_medica_web.enums.CargoNome;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record UsuarioUpdateDTO(

        String nome,

        @Email(message = "O email deve ser válido")
        String email,

        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String senha,

        @Enumerated
        CargoNome cargo,

        @NotNull(message = "O campo 'isEnable' deve ser true ou false")
        Boolean isEnable
) {
}
