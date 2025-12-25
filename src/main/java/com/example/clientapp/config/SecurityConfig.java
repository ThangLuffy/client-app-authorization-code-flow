package com.example.clientapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String[] PUBLIC_URLS = {
            "/",
            "/home",
            "/login",
            "/css/**",
            "/js/**",
            "/images/**",
            "/webjars/**"};

    private final KeyCloakLogoutHandler keycloakLogoutHandler;
    private final OidcAuthenticationFailureHandler authenticationFailureHandler;

    public SecurityConfig(KeyCloakLogoutHandler keycloakLogoutHandler, OidcAuthenticationFailureHandler authenticationFailureHandler) {
        this.keycloakLogoutHandler = keycloakLogoutHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

//  TODO need review
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/logout")
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(withDefaults())
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/secured", true)
                        .failureUrl("/login?error=true")
                        .failureHandler(authenticationFailureHandler)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler(keycloakLogoutHandler)
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/login?logout=true")
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );
        return http.build();
    }
}
