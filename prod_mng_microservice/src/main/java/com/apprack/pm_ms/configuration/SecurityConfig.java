
package com.apprack.pm_ms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)  // Updated syntax for disabling CSRF
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/products/add_category", "products/add_product").permitAll()  // Allow access to user creation without authentication
                        .anyRequest().authenticated()  // Require authentication for all other requests
                );
        return http.build();

    }
}
