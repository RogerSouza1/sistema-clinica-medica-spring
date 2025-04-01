package com.orange.sistema_clinca_medica_web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    public static final String[] ENDPOINTS_WHITELIST = {
            "/auth/login",
            "/usuario/cadastrar"
    };
    public static final String[] ENDPOINTS_BLACKLIST = {
            "/profile/listar",
            "/auth/teste"
    };
    public static final String[] ENDPOINTS_PACIENTE = {
            "/auth/teste/paciente"
    };
    public static final String[] ENDPOINTS_MEDICO = {
            "/auth/teste/medico"
    };
    public static final String[] ENDPOINTS_USUARIO_PADRAO = {
            "/auth/teste/usuario-padrao"
    };
    public static final String[] ENDPOINTS_USUARIO_ADMIN = {
            "/usuario/listar",
            "/usuario/**"
    };

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // Desativa a proteção contra CSRF
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                        .requestMatchers(ENDPOINTS_BLACKLIST).authenticated()
                        .requestMatchers(ENDPOINTS_PACIENTE).hasRole("PACIENTE")
                        .requestMatchers(ENDPOINTS_MEDICO).hasRole("MEDICO")
                        .requestMatchers(ENDPOINTS_USUARIO_PADRAO).hasRole("USUARIO_PADRAO")
                        .requestMatchers(ENDPOINTS_USUARIO_ADMIN).hasRole("USUARIO_ADMINISTRADOR")
                        .anyRequest().denyAll()
                )
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
