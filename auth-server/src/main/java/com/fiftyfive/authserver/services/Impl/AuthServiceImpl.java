package com.fiftyfive.authserver.services.Impl;

import com.fiftyfive.authserver.dtos.*;
import com.fiftyfive.authserver.exceptions.InvalidLoginException;
import com.fiftyfive.authserver.exceptions.LogoutFailureException;
import com.fiftyfive.authserver.services.AuthService;
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

import static com.fiftyfive.authserver.commons.Constants.*;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private WebClient webClient;
    @Value(CLIENT_ID_PROP)
    private String clientId;

    @Value(CLIENT_SECRET_PROP)
    private String clientSecret;
    @Value(GRANT_TYPE_PROP)
    private String grantType;


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
                    .uri(LOGIN_URI)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(map))
                    .retrieve()
                    .toEntity(LoginResponseDTO.class)
                    .block();
            return response.getBody();
        } catch (RuntimeException ex) {
            throw new InvalidLoginException(INVALID_LOGIN, ex.getCause());
        }
    }

    @Override
    public Response logout(TokenDTO tokenDTO) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add(CLIENT_ID, clientId);
            requestBody.add(CLIENT_SECRET, clientSecret);
            requestBody.add(REFRESH_TOKEN, tokenDTO.getToken());
            ClientResponse response = webClient.post()
                    .uri(LOGOUT_API)
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

    @Override
    public IntrospectResponse introspect(TokenDTO tokenDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(CLIENT_ID, clientId);
        map.add(CLIENT_SECRET, clientSecret);
        map.add(TOKEN, tokenDTO.getToken());
        ResponseEntity<IntrospectResponse> response = webClient.post()
                .uri(INTROSPECT_API)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(map)
                .retrieve()
                .toEntity(IntrospectResponse.class)
                .block();
        return response.getBody();
    }
}
