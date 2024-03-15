package io.github.fabiooliveira.authapi.configs.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração para a documentação da API
 */
@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Auth API") // Nome da API
                .description("Api para autenticação de usuários") // Descrição da API
                .version("v1.0.0") // Versão da API
                .license(new License().name("Apache 2.0").url("http://springdoc.org"))); // Licença da API
    }
}
