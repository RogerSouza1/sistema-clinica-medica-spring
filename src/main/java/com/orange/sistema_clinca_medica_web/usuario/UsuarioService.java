package com.orange.sistema_clinca_medica_web.usuario;

import com.orange.sistema_clinca_medica_web.enums.CargoNome;
import com.orange.sistema_clinca_medica_web.security.JwtTokenService;
import com.orange.sistema_clinca_medica_web.security.RecoveryJwtTokenDto;
import com.orange.sistema_clinca_medica_web.security.SecurityConfiguration;
import com.orange.sistema_clinca_medica_web.security.UsuarioDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    public RecoveryJwtTokenDto autenticarUsuario(UsuarioLoginDTO usuarioLoginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(usuarioLoginDTO.email().toLowerCase(), usuarioLoginDTO.senha());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        UsuarioDetailsImp usuarioDetailsImp = (UsuarioDetailsImp) authentication.getPrincipal();

        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(usuarioDetailsImp));
    }

    public Usuario criarUsuario(UsuarioRequestDTO usuarioRequestDTO) {
                Usuario novoUsuario = Usuario.builder()
                .nome(usuarioRequestDTO.nome())
                .email(usuarioRequestDTO.email())
                .senha(securityConfiguration.passwordEncoder().encode(usuarioRequestDTO.senha()))
                .cargo(CargoNome.valueOf(usuarioRequestDTO.cargo().toUpperCase()))
                .build();

        return usuarioRepository.save(novoUsuario);
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

}
