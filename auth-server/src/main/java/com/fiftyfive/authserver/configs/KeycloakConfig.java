package com.fiftyfive.authserver.configs;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;

@KeycloakConfiguration
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakConfig {

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issueUrl;

    /**
     * Use properties in application.properties instead of keycloak.json
     */
    @Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {

        return new KeycloakSpringBootConfigResolver();
    }

    /**
     * JwtDecoder helps in decoding ,verifying and validating jwt
     * Create a JwtDecoder using the Keycloak issuer URL
     * JwtDecoder, which decodes String tokens into validated instances of Jwt
     */
    @Bean
    public JwtDecoder jwtDecoder() {

        return JwtDecoders.fromIssuerLocation(issueUrl);
    }
}
