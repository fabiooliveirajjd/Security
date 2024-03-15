package io.github.fabiooliveira.authapi.services;

import io.github.fabiooliveira.authapi.dtos.UsuarioDto;

public interface UsuarioService {

    // Método para salvar um usuário
    public UsuarioDto salvar(UsuarioDto usuarioDto);
}
