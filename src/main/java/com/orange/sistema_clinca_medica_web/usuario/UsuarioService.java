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
import java.util.NoSuchElementException;

import java.util.List;
import java.util.Optional;

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
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuarioLoginDTO.email().toLowerCase(), usuarioLoginDTO.senha());

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
                .isEnable(true)
                .build();

        return usuarioRepository.save(novoUsuario);
    }

    @Transactional
    public Usuario atualizarUsuario(Long id, UsuarioUpdateDTO usuarioUpdateDTO) {

    Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));


        if (usuarioUpdateDTO.nome() != null) {
            usuario.setNome(usuarioUpdateDTO.nome());
        }

        if (usuarioUpdateDTO.email() != null) {
            usuario.setEmail(usuarioUpdateDTO.email());
        }

        if (usuarioUpdateDTO.senha() != null) {
            usuario.setSenha(securityConfiguration.passwordEncoder().encode(usuarioUpdateDTO.senha()));
        }

        if (usuarioUpdateDTO.cargo() != null) {
            usuario.setCargo(usuarioUpdateDTO.cargo());
        }

        if (usuarioUpdateDTO.isEnable() != null) {
            usuario.setIsEnable(usuarioUpdateDTO.isEnable());
        }

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }
}
