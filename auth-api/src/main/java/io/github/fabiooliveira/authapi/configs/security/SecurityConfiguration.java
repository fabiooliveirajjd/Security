package io.github.fabiooliveira.authapi.configs.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

/**
 * Classe de configuração de segurança
 */
@Configuration // Define a classe como uma classe de configuração
@EnableWebSecurity // Habilita a segurança web na aplicação
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final SecurityFilter securityFilter;

    /**
     * Método que configura as regras de segurança da aplicação
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean // Define o método como um bean gerenciado pelo Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // Desabilita o CSRF podendo acessar o endpoint sem autenticação
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define a política de criação de sessão como Stateless
                .authorizeHttpRequests(authoriza -> authoriza
                        .requestMatchers(HttpMethod.GET, "/usuarios/admin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/usuarios/user").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .anyRequest().authenticated() // Define que qualquer outra requisição necessita de autenticação
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona o filtro de segurança antes do filtro de autenticação

                .build();
    }

    /**
     * Método que configura o encoder de senha
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Método que configura o gerenciador de autenticação
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
