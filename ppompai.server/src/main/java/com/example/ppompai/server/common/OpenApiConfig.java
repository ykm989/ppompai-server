package com.example.ppompai.server.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApiSecurityConfig() {
        // 1) SecurityScheme 정의: HTTP bearer, JWT
        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // 2) SecurityRequirement: 모든 API에 위 스킴 적용
        SecurityRequirement securityReq = new SecurityRequirement()
                .addList("bearerAuth");   // "bearerAuth"는 아래 Components에 등록한 이름과 일치해야 함

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", bearerScheme)
                )
                .addSecurityItem(securityReq);
    }
}