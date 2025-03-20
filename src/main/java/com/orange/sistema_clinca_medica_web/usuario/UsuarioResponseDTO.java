package com.orange.sistema_clinca_medica_web.usuario;

import com.orange.sistema_clinca_medica_web.enums.CargoNome;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String senha,
        CargoNome cargo) {

    public UsuarioResponseDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getCargo());
    }
}
