package io.github.fabiooliveira.authapi.repositorys;

import io.github.fabiooliveira.authapi.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Interface para representar um repositório de usuário
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para buscar um usuário pelo login
    Usuario findByLogin(String login);
}
