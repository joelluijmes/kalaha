package dev.joell.kalaha.Auth;

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
        // Disable all checks (for now)
        http.authorizeHttpRequests()
                .requestMatchers("/**")
                .permitAll();

        // Disable CSRF to allow POST requests from curl
        http.csrf().disable();

        return http.build();
    }
}