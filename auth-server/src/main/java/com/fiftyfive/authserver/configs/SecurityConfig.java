package com.fiftyfive.authserver.configs;

import jakarta.servlet.ServletException;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.authentication.KeycloakLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.reactive.function.client.WebClient;

@KeycloakConfiguration
public class SecurityConfig {

    /**
     * Registers the KeycloakAuthenticationProvider with the authentication manager.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        System.out.println("Calling --------------->flow1");
        KeycloakAuthenticationProvider keycloakAuthenticationProvider
                = new KeycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(
                new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    /**
     * Provide a session authentication strategy bean which should be of type
     * RegisterSessionAuthenticationStrategy for public or confidential applications
     * and NullAuthenticatedSessionStrategy for bearer-only applications.
     */
    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        System.out.println("Calling --------------->flow2");
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Calling --------------->flow3");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable());
        http.authorizeHttpRequests(requests -> requests.requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated());
        http.oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer ->
                httpSecurityOAuth2ResourceServerConfigurer.jwt(jwtConfigurer
                        -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)));
        http.logout(logoutConfigurer ->

                logoutConfigurer.logoutUrl("/auth/logout/")
                        .invalidateHttpSession(true)
                        .addLogoutHandler((request, response, auth) -> {
                    try {
                        request.logout();
                    } catch (ServletException e) {
                        System.out.println(e.getMessage());
                    }
                })
        );
        return http.build();
    }


}