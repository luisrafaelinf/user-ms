package com.nisum.user.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
// import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
// @EnableSwagger2
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API de Gestión de Usuarios",
        version = "1.0",
        description = "Documentación de la API para la gestión de usuarios"
    )
)
public class OpenApiConfig {
}
