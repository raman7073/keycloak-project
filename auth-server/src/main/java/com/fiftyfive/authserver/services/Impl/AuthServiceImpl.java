package com.fiftyfive.authserver.services.Impl;

import com.fiftyfive.authserver.dtos.*;
import com.fiftyfive.authserver.exceptions.InvalidUserNamePasswordException;
import com.fiftyfive.authserver.exceptions.LogoutFailureException;
import com.fiftyfive.authserver.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;


@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private WebClient webClient;

    @Value("${spring.security.oauth2.client.registration.oauth2-client-credentials.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.oauth2-client-credentials.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.oauth2-client-credentials.authorization-grant-type}")
    private String grantType;

    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
    private String loginUri;

    @Value("${spring.security.oauth2.client.provider.keycloak.end-session-endpoint}")
    private String logoutUri;
    private final String CLIENT_ID = "client_id";
    private final String CLIENT_SECRET = "client_secret";
    private final String GRANT_TYPE = "grant_type";
    private final String SCOPE = "scope";
    private final String OPENID = "openid";
    private final String USERNAME = "username";
    private final String PASSWORD = "password";
    private final String INVALID_LOGIN = "Invalid Username Or Password";
    private final String INVALID_LOGOUT = "Logout failure";
    private final String REFRESH_TOKEN = "refresh_token";
    private final String LOGGED_OUT = "Logged Out Successfully";

    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(CLIENT_ID, clientId);
        map.add(CLIENT_SECRET, clientSecret);
        map.add(GRANT_TYPE, grantType);
        map.add(SCOPE, OPENID);
        map.add(USERNAME, loginDTO.getUsername());
        map.add(PASSWORD, loginDTO.getPassword());
        try {
            ResponseEntity<LoginResponseDTO> response = webClient.post()
                    .uri(loginUri)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(map))
                    .retrieve()
                    .toEntity(LoginResponseDTO.class)
                    .block();
            return response.getBody();
        } catch (RuntimeException ex) {
            throw new InvalidUserNamePasswordException(INVALID_LOGIN, ex.getCause());
        }
    }

    @Override
    public Response logout(String refreshToken) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add(CLIENT_ID, clientId);
            requestBody.add(CLIENT_SECRET, clientSecret);
            requestBody.add(REFRESH_TOKEN, refreshToken);
            ClientResponse response = webClient.post()
                    .uri(logoutUri)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .accept(MediaType.TEXT_HTML)
                    .body(BodyInserters.fromFormData(requestBody))
                    .exchange()
                    .block();

            Response res = new Response();
            if (response.statusCode().is2xxSuccessful()) {
                res.setMessage(LOGGED_OUT);
            }
            return res;
        } catch (RuntimeException exception) {
            throw new LogoutFailureException(INVALID_LOGOUT, exception.getCause());
        }
    }
}
