package io.github.fabiooliveira.authapi.configs.security;

import io.github.fabiooliveira.authapi.models.Usuario;
import io.github.fabiooliveira.authapi.repositorys.UsuarioRepository;
import io.github.fabiooliveira.authapi.services.AutenticacaoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Classe que implementa a segurança da aplicação
 */
@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final AutenticacaoService autenticacaoService;

    private final UsuarioRepository usuarioRepository;

    /**
     * Método que implementa a segurança da aplicação
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extraiTokeHeader(request); // Variável token do tipo String que recebe o retorno do método extraiTokeHeader
        if (token != null) { // Condição que verifica se a variável token é diferente de null
            String login = autenticacaoService.validaTokenJwt(token); // Variável login do tipo String que recebe o retorno do método validaTokenJwt
            Usuario usuario = usuarioRepository.findByLogin(login); // Variável usuario do tipo Usuario que recebe o retorno do método findByLogin

            // Variável autentication do tipo UsernamePasswordAuthenticationToken que recebe um novo objeto UsernamePasswordAuthenticationToken
            var autentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); //

            // Define o contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(autentication);
        }
        // Seta o token no cabeçalho da resposta
        filterChain.doFilter(request, response);
    }

    /**
     * Método que extrai o token do cabeçalho da requisição
     * @param request
     * @return
     */
    public String extraiTokeHeader(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization"); // Variável authHeader do tipo String
        if (authHeader == null) { // Condição que verifica se a variável authHeader é nula
            return null; // Retorna null
        }
        if (!authHeader.split(" ")[0].equals("Bearer")) { // Condição que verifica se o primeiro elemento do array authHeader é diferente de "Bearer"
            return null; // Retorna null
        }
        return authHeader.split(" ")[1]; // Retorna o segundo elemento do array authHeader
    }
}
