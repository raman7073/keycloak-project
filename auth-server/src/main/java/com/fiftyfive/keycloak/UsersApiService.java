package com.fiftyfive.keycloak;

import com.fasterxml.jackson.databind.JsonNode;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.models.KeycloakSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UsersApiService {
    private KeycloakSession session;
    private final String ID = "id";
    private final String USERNAME = "username";
    private final String FIRSTNAME = "firstName";
    private final String LASTNAME = "lastName";
    private final String EMAIL = "email";
    private final String PASSWORD = "password";
    private final String BASE_URI = "http://localhost:8080/users/";

    public UsersApiService(KeycloakSession session) {
        this.session = session;
    }

    User getUserDetails(String username) {

        try {
            JsonNode node = SimpleHttp.doGet(BASE_URI + username, this.session).asJson();
            String id = node.has(ID) ? node.get(ID).asText() : null;
            String username1 = node.has(USERNAME) ? node.get(USERNAME).asText() : null;
            String firstName = node.has(FIRSTNAME) ? node.get(FIRSTNAME).asText() : null;
            String lastName = node.has(LASTNAME) ? node.get(LASTNAME).asText() : null;
            String email = node.has(EMAIL) ? node.get(EMAIL).asText() : null;
            String password = node.has(PASSWORD) ? node.get(PASSWORD).asText() : null;
            User user = new User( username1, email, firstName, lastName, password);
            return user;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    List<User> getAllUsers() {

        try {
            List<User> userList = new ArrayList<>();
            JsonNode jsonNode = SimpleHttp.doGet(BASE_URI, this.session).asJson();
            for (JsonNode node : jsonNode) {
                String id = node.has(ID) ? node.get(ID).asText() : null;
                String username = node.has(USERNAME) ? node.get(USERNAME).asText() : null;
                String firstName = node.has(FIRSTNAME) ? node.get(FIRSTNAME).asText() : null;
                String lastName = node.has(LASTNAME) ? node.get(LASTNAME).asText() : null;
                String email = node.has(EMAIL) ? node.get(EMAIL).asText() : null;
                String password = node.has(PASSWORD) ? node.get(PASSWORD).asText() : null;
                User user = new User( username, email, firstName, lastName, password);
                userList.add(user);
            }
            return userList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
