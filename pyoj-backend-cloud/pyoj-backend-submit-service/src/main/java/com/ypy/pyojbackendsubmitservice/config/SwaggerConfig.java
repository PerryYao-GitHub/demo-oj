package com.ypy.pyojbackendsubmitservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Cookie 认证：JSESSIONID
        SecurityScheme cookieScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name("JSESSIONID");

        // JWT Bearer Token 认证
        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        // 设置 Gateway 中的前缀路径
        Server server = new Server();
        server.setUrl("/api/submit");

        return new OpenAPI()
                .info(new Info()
                        .title("PYOJ Backend Submit Service API")
                        .version("v1.0"))
                .servers(Collections.singletonList(server))
                .components(new Components()
                        .addSecuritySchemes("cookieAuth", cookieScheme)
                        .addSecuritySchemes("bearerAuth", bearerScheme))
                .addSecurityItem(new SecurityRequirement()
                        .addList("cookieAuth")
                        .addList("bearerAuth"));
    }
}

