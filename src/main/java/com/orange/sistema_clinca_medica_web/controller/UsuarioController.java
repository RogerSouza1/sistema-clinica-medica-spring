package com.orange.sistema_clinca_medica_web.controller;

import com.orange.sistema_clinca_medica_web.security.RecoveryJwtTokenDto;
import com.orange.sistema_clinca_medica_web.usuario.UsuarioLoginDTO;
import com.orange.sistema_clinca_medica_web.usuario.UsuarioRequestDTO;
import com.orange.sistema_clinca_medica_web.usuario.UsuarioResponseDTO;
import com.orange.sistema_clinca_medica_web.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> autenticarUsuario(@RequestBody UsuarioLoginDTO usuarioLoginDTO) {
        RecoveryJwtTokenDto token = usuarioService.autenticarUsuario(usuarioLoginDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> criarUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        usuarioService.cirarUsuario(usuarioRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
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

}
