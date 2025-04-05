package com.orange.sistema_clinca_medica_web.security;

import java.util.List;

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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration implements WebMvcConfigurer {

    public static final String[] ENDPOINTS_WHITELIST = {
            "/auth/login",
            "/usuario/**",
            "/usuario/cadastrar",
            "/usuario/listar"
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
                "/auth/teste/usuario-admin"
    };

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .cors(cors -> cors.configurationSource(request -> {
                var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                corsConfig.setAllowedOrigins(List.of("http://localhost:5173")); // Permita a origem do frontend
                corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
                corsConfig.setAllowedHeaders(List.of("*"));
                corsConfig.setAllowCredentials(true);
                return corsConfig;
            }))
            .csrf(AbstractHttpConfigurer::disable)
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
