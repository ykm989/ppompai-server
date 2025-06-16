package com.example.ppompai.server.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/api/auth/**",                // 로그인·회원가입 등
                        "/swagger-ui/**",              // Swagger UI 정적 리소스
                        "/v3/api-docs/**",             // OpenAPI JSON
                        "/swagger-resources/**",       // Swagger 리소스
                        "/webjars/**",                 // Swagger UI 가 쓰는 JS/CSS
                        "/swagger/**"                  // 다른 버전 호환
                );
    }
}
