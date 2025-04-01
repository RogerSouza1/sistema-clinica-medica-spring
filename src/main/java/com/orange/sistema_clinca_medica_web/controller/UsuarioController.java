package com.orange.sistema_clinca_medica_web.controller;

import com.orange.sistema_clinca_medica_web.usuario.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuarioRequestDTO.email());

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return new ResponseEntity<>(new UsuarioResponseDTO(usuario), HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = usuarioService.criarUsuario(usuarioRequestDTO);
        return new ResponseEntity<>(new UsuarioResponseDTO(usuario), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public List<UsuarioResponseDTO> getUsuarios() {
        return usuarioService.getUsuarios()
                .stream()
                .map(UsuarioResponseDTO::new)
                .toList();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioUpdateDTO usuarioUpdateDTO) {
        Usuario usuario = usuarioService.atualizarUsuario(id, usuarioUpdateDTO);
        return new ResponseEntity<>(new UsuarioResponseDTO(usuario), HttpStatus.OK);
    }
}
