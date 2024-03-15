package io.github.fabiooliveira.authapi.services.impl;

import io.github.fabiooliveira.authapi.dtos.UsuarioDto;
import io.github.fabiooliveira.authapi.exceptions.BusinessException;
import io.github.fabiooliveira.authapi.models.Usuario;
import io.github.fabiooliveira.authapi.repositorys.UsuarioRepository;
import io.github.fabiooliveira.authapi.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Método para salvar um usuário
     * @param usuarioDto
     * @return
     */
    @Override
    public UsuarioDto salvar(UsuarioDto usuarioDto) {
        Usuario usuarioJaExiste = usuarioRepository.findByLogin(usuarioDto.login());
        if (usuarioJaExiste != null) { // Verifica se o usuário já existe
            throw new BusinessException("Usuário já existe!"); // Se sim, lança uma exceção
        }
        var passwordHash = passwordEncoder.encode(usuarioDto.senha()); // Gera o hash da senha
        Usuario entity = new Usuario(usuarioDto.nome(), usuarioDto.login(), passwordHash, usuarioDto.role()); // Cria uma entidade de usuário
        Usuario novoUsuario = usuarioRepository.save(entity); // Salva o usuário
        return new UsuarioDto(novoUsuario.getNome(), novoUsuario.getLogin(), novoUsuario.getSenha(), novoUsuario.getRole()); // Retorna o usuário salvo
    }
}

