package io.github.fabiooliveira.authapi.dtos;

import io.github.fabiooliveira.authapi.enuns.RoleEnum;

// Record representa um DTO para usuário
public record UsuarioDto(
        String nome,
        String login,
        String senha,
        RoleEnum role
) {
}
