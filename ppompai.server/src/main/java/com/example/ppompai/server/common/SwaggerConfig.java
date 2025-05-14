package com.example.ppompai.server.common;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "뿜빠이 RESTful API",
                description = "앱에서 사용하는 RESTful API 문서",
                version = "v0.0.1"
        )
)
public class SwaggerConfig {

}
