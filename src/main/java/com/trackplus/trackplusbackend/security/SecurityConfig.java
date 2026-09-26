package com.trackplus.trackplusbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // PUBLIC APIs
                        // =========================

                        // Login
                        .requestMatchers("/api/auth/login")
                        .permitAll()

                        // Public shipment tracking
                        .requestMatchers("/api/shipments/track/**")
                        .permitAll()

                        // Basic hello endpoint
                        .requestMatchers("/api/hello")
                        .permitAll()


                        // =========================
                        // ADMIN APIs
                        // =========================

                        // User management
                        .requestMatchers("/api/users/**")
                        .hasAuthority("ROLE_ADMIN")

                        // Create routes
                        .requestMatchers(HttpMethod.POST, "/api/routes")
                        .hasAuthority("ROLE_ADMIN")

                        // Create shipments
                        .requestMatchers(HttpMethod.POST, "/api/shipments")
                        .hasAuthority("ROLE_ADMIN")


                        // =========================
                        // ADMIN + DRIVER APIs
                        // =========================

                        // View routes
                        .requestMatchers(HttpMethod.GET, "/api/routes")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_DRIVER")

                        // View shipments
                        .requestMatchers(HttpMethod.GET, "/api/shipments")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_DRIVER")

                        // View shipment history
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/shipments/*/history"
                        )
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_DRIVER")


                        // =========================
                        // DRIVER APIs
                        // =========================

                        // Driver updates shipment status
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/shipments/*/status"
                        )
                        .hasAuthority("ROLE_DRIVER")


                        // =========================
                        // EVERYTHING ELSE
                        // =========================

                        .anyRequest()
                        .authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}