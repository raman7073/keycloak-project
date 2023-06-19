package com.fiftyfive.authserver.configs;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;

@KeycloakConfiguration
public class KeycloakConfig {

    /**
     * Use properties in application.properties instead of keycloak.json
     */
    @Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {

        System.out.println("Calling --------------->flow5");
        return new KeycloakSpringBootConfigResolver();
    }

    /**
    *JwtDecoder helps in decoding ,verifying and validating jwt
    *
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        /**
         * Create a JwtDecoder using the Keycloak issuer URL and realm
          */
        System.out.println("Calling --------------->flow4");
        String issuerUrl = "http://localhost:8180/realms/umsdev";
        String realm = "umsdev";
        String jwkSetUrl = issuerUrl + "/protocol/openid-connect/certs";
        return JwtDecoders.fromIssuerLocation(issuerUrl);
    }
}
