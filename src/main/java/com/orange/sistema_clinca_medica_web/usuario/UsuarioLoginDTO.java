package com.orange.sistema_clinca_medica_web.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.beans.XMLEncoder;

public record UsuarioLoginDTO(

        @Email(message = "O email deve ser válido")
        @NotNull(message = "O email é obrigatório")
        String email,

        @NotNull(message = "A senha deve ser obrigatória")
        String senha
) {
}
