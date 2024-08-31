package com.ayo.monnify_api_clone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // for DI
public class SecurityConfig {

    // Inject all dependency
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilterChain jwtAuthFilter;
    private final JwtAuthFilterChainAPI jwtAuthFilterChainAPI;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .requestMatchers("/api/v1/auth/**", "/api/v1/auth/**", "/auth/**").permitAll()
            .requestMatchers("/merchants/**").authenticated()
            .requestMatchers("/api/v1/**").authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthFilterChainAPI, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
