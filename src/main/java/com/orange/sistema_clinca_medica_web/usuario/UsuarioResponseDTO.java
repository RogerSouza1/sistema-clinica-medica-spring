package com.orange.sistema_clinca_medica_web.usuario;

import com.orange.sistema_clinca_medica_web.enums.CargoNome;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        CargoNome cargo,
        Boolean enable) {

    public UsuarioResponseDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getCargo(), usuario.isEnable());
    }
}
