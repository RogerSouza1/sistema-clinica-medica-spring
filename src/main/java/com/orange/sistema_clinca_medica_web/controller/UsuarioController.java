package com.orange.sistema_clinca_medica_web.controller;

import com.orange.sistema_clinca_medica_web.security.RecoveryJwtTokenDto;
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> autenticarUsuario(@RequestBody UsuarioLoginDTO usuarioLoginDTO) {
        RecoveryJwtTokenDto token = usuarioService.autenticarUsuario(usuarioLoginDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuarioRequestDTO.email());

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return new ResponseEntity<>(new UsuarioResponseDTO(usuario), HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = usuarioService.cirarUsuario(usuarioRequestDTO);
        return new ResponseEntity<>(new UsuarioResponseDTO(usuario), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public List<UsuarioResponseDTO> getUsuarios() {
        return usuarioService.getUsuarios()
                .stream()
                .map(UsuarioResponseDTO::new)
                .toList();
    }


    @GetMapping("/teste")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/teste/paciente")
    public ResponseEntity<String> getAutenticacaoPaciente() {
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/teste/usuario-admin")
    public ResponseEntity<String> getAutenticacaoUsuarioAdmin() {
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }

}
