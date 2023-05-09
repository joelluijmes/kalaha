package dev.joell.kalaha.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // TODO: Disable all checks (for now), to be expanded later
        // to validate the JWT token.
        http.authorizeHttpRequests()
                .requestMatchers("/**")
                .permitAll();

        // Disable CSRF to allow POST requests from curl
        http.csrf().disable();

        return http.build();
    }
}