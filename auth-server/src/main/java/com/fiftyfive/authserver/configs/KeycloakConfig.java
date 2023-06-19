package com.fiftyfive.authserver.configs;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;

@KeycloakConfiguration
public class KeycloakConfig {

    /**
     * Use properties in application.properties instead of keycloak.json
     */
    @Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {

        return new KeycloakSpringBootConfigResolver();
    }

    /**
     * JwtDecoder helps in decoding ,verifying and validating jwt
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        /**
         * Create a JwtDecoder using the Keycloak issuer URL and realm
         */
        String issuerUrl = "http://localhost:8180/realms/userdev";
        String realm = "user-client";
        String jwkSetUrl = issuerUrl + "/protocol/openid-connect/certs";
        return JwtDecoders.fromIssuerLocation(issuerUrl);
    }
}
