package io.github.fabiooliveira.authapi.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.fabiooliveira.authapi.dtos.AuthDto;
import io.github.fabiooliveira.authapi.models.Usuario;
import io.github.fabiooliveira.authapi.repositorys.UsuarioRepository;
import io.github.fabiooliveira.authapi.services.AutenticacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Classe para representar um serviço de autenticação
 */
@Service
@RequiredArgsConstructor
public class AutenticacaoServiceImpl implements AutenticacaoService {

    private final UsuarioRepository usuarioRepository;

    // Método para carregar o usuário pelo login
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(login);
    }

    // Método para obter o token
    @Override
    public String obterToken(AuthDto authDto) {
        Usuario usuario =  usuarioRepository.findByLogin(authDto.login());
        return geraTokenJwt(usuario);
    }

    // Método para gerar o token JWT
    public String geraTokenJwt(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret"); // Serve para assinar o token
            return JWT.create() // Cria o token
                    .withIssuer("auth-api") // Quem está emitindo o token
                    .withSubject(usuario.getLogin()) // Quem é o dono do token
                    .withExpiresAt(geraDataExpiracao()) // Quando o token expira
                    .sign(algorithm); // Assina o token
        } catch (JWTCreationException exception) { // Trata exceção
            throw new RuntimeException("Erro ao tentar gerar o token! " + exception.getMessage()); // Lança uma exceção
        }
    }

    // Método para gerar a data de expiração do token
    private Instant geraDataExpiracao() {
        return LocalDateTime.now() // Pega a data e hora atual
                .plusHours(8) // Adiciona 8 horas
                .toInstant(ZoneOffset.of("-03:00")); // Converte para o formato de data e hora, nesse caso, para o fuso horário de Brasília.
    }

    // Método para validar o token JWT
    public String validaTokenJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret"); // Serve para assinar o token

            return JWT.require(algorithm) // Requer que o token seja assinado com o algoritmo HMAC256
                    .withIssuer("auth-api") // Quem está emitindo o token
                    .build() // Constroi o token
                    .verify(token) // Verifica o token
                    .getSubject(); // Retorna o dono do token

        } catch (JWTVerificationException exception) {
            return ""; // Retorna uma string vazia caso o token seja inválido
        }
    }

}
