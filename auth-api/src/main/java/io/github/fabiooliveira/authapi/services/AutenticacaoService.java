package io.github.fabiooliveira.authapi.services;

import io.github.fabiooliveira.authapi.dtos.AuthDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AutenticacaoService extends UserDetailsService {

    // Método para obter um token
    public String obterToken(AuthDto authDto);

    // Método para validar um token
    public String validaTokenJwt(String token);
}