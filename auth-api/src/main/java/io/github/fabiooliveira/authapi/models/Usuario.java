package io.github.fabiooliveira.authapi.models;

import io.github.fabiooliveira.authapi.enuns.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


/**
 * Classe para representar um usuário
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USUARIO")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String login;
    @Column(nullable = false)
    private String senha;
    @Column(nullable = false)
    private RoleEnum role;

    public Usuario(String nome, String login, String senha, RoleEnum role) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == role.ADMIN) // Se o usuário for admin, ele terá as duas roles
            return List.of( // Retorna uma lista de roles
                    new SimpleGrantedAuthority("ROLE_ADMIN"), // Role admin
                    new SimpleGrantedAuthority("ROLE_USER") // Role user
            );
        return List.of( // Se não, ele terá apenas a role user
                new SimpleGrantedAuthority("ROLE_USER") // Role user
        );
    }


    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
