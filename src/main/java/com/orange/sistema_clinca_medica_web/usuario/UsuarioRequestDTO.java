package com.orange.sistema_clinca_medica_web.usuario;

import com.orange.sistema_clinca_medica_web.enums.CargoNome;

public record UsuarioRequestDTO(
        String nome,
        String email,
        String senha,
        CargoNome cargo
) {
}
