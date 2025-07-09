package com.lgm.my_mong.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                // 2. 세션 사용 안함 (JWT는 Stateless)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3. URL별 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/api/health/**").permitAll()
                        .requestMatchers("/v1/api/auth/signup").permitAll()   // 회원가입 허용
                        .requestMatchers("/v1/api/auth/signin").permitAll()   // 로그인 허용// 로그인/회원가입 허용
                        .requestMatchers("/v1/api/ai/**").permitAll()   //
                        .anyRequest().authenticated()                    // 나머지는 인증 필요
                )

                // 4. JWT 필터를 Spring Security 필터 체인에 추가
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
