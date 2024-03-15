package io.github.fabiooliveira.authapi.controllers;

import io.github.fabiooliveira.authapi.dtos.AuthDto;
import io.github.fabiooliveira.authapi.services.AutenticacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

    private static final Logger logger = LogManager.getLogger(AutenticacaoController.class);

    private final AuthenticationManager authenticationManager;

    private final AutenticacaoService autenticacaoService;

    // Método para autenticar o usuário
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String auth(@RequestBody AuthDto authDto) {
        var usuarioAutenticationToken = new UsernamePasswordAuthenticationToken(authDto.login(), authDto.senha());
        authenticationManager.authenticate(usuarioAutenticationToken);
        String token = autenticacaoService.obterToken(authDto);
        logger.info("Token gerado: {}", token);
        return token;
    }
}
