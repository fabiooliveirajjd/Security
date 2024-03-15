package io.github.fabiooliveira.authapi.configs.sql;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Classe de configuração para o ambiente de desenvolvimento
 */
@Configuration
@Profile("test")
public class TestConfig {
}
