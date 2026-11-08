package com.university.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails student = User.withUsername("student")
                .password("{noop}spass")
                .roles("STUDENT")
                .build();

        UserDetails teacher = User.withUsername("teacher")
                .password("{noop}teapass")
                .roles("TEACHER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("{noop}adpass")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(student, teacher, admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(auth -> auth
                        // Public frontend assets & H2 console
                        .requestMatchers("/", "/index.html", "/favicon.ico", "/error", "/h2-console/**").permitAll()
                        // STUDENT, TEACHER, ADMIN can view students
                        .requestMatchers(HttpMethod.GET, "/api/students", "/api/students/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                        // TEACHER and ADMIN can update students
                        .requestMatchers(HttpMethod.PUT, "/api/students/**").hasAnyRole("TEACHER", "ADMIN")
                        // Only ADMIN can create students
                        .requestMatchers(HttpMethod.POST, "/api/students", "/api/students/**").hasRole("SADMIN")
                        // Only ADMIN can delete students
                        .requestMatchers(HttpMethod.DELETE, "/api/students/**").hasRole("ADMIN")
                        // Other endpoints require authentication
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
