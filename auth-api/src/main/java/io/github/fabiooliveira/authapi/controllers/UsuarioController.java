package io.github.fabiooliveira.authapi.controllers;

import io.github.fabiooliveira.authapi.dtos.UsuarioDto;
import io.github.fabiooliveira.authapi.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public UsuarioDto salvar(@RequestBody UsuarioDto usuarioDto) {
        return usuarioService.salvar(usuarioDto);
    }

    // Método para pegar a permissão de administrador
    @GetMapping("/admin")
    private String getAdmin() {
        return "permissão de administrador";
    }

    // Método para pegar a permissão de usuário
    @GetMapping("/user")
    private String getUser() {
        return "permissão de usuário";
    }
}
