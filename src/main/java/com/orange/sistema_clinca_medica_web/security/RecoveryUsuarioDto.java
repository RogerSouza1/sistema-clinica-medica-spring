package com.orange.sistema_clinca_medica_web.security;

import com.orange.sistema_clinca_medica_web.enums.CargoNome;

import java.util.List;

public record RecoveryUsuarioDto(
        Long id,
        String email,
        List<CargoNome> cargos
) {
}
