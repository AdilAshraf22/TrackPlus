package com.trackplus.trackplusbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // PUBLIC APIs
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/shipments/track/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/api/hello").permitAll()

                        // ADMIN APIs
                        .requestMatchers("/api/users/**")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/routes")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/shipments")
                        .hasAuthority("ROLE_ADMIN")

                        // ADMIN + DRIVER
                        .requestMatchers(HttpMethod.GET, "/api/routes")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_DRIVER")

                        .requestMatchers(HttpMethod.GET, "/api/shipments")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_DRIVER")

                        .requestMatchers(HttpMethod.GET, "/api/shipments/*/history")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_DRIVER")

                        // DRIVER
                        .requestMatchers(HttpMethod.PUT, "/api/shipments/*/status")
                        .hasAuthority("ROLE_DRIVER")

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}