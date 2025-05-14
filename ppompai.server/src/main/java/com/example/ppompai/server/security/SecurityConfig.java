package com.example.ppompai.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 비활성화 (Swagger UI의 POST/PUT 테스트 시 CSRF 토큰 오류 방지)
                .csrf(csrf -> csrf.disable())

                // URL 별 접근 권한 옵션 설정
                .authorizeHttpRequests(auth -> auth
                        // Swagger UI와 API 명세 엔드포인트에 대해서는 누구나 접근 허용
                        .requestMatchers(
                                "/api/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        // 그 외 모든 요청은 인증된 사용자만 접근 허용
                        .anyRequest().authenticated()
                )
                // 기본 로그인 폼 비활성화 -> 로그인 화면 안뜸
                .formLogin(form -> form.disable())
                // HTTP Basic 인증 비활성화 -> 브라우저 기본 인증 팝업도 안 뜸
                .httpBasic(basic -> basic.disable()
                );

                // 설정을 적용한 FilterChain 객체를 반환해서, 실제 보안 필터 체인으로 등록
                return http.build();
    }
}
