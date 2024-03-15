package io.github.fabiooliveira.authapi.enuns;

import lombok.Getter;

// Enum para representar os papéis de um usuário
@Getter
public enum RoleEnum {
    ADMIN("admin"),
    USER("user");

    private String role;

    RoleEnum(String role) {
        this.role = role;
    }
}