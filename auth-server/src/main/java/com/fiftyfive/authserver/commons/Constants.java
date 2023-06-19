package com.fiftyfive.authserver.commons;

public class Constants {
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String GRANT_TYPE = "grant_type";
    public static final String SCOPE = "scope";
    public static final String OPENID = "openid";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String TOKEN = "refresh_token";
    public static final String INVALID_LOGIN = "Login failure";
    public static final String INVALID_LOGOUT = "Logout failure";
    public static final String LOGIN_URI = "http://localhost:8180/realms/userdev/protocol/openid-connect/token";
    public static final String LOGOUT_API = "http://localhost:8180/realms/userdev/protocol/openid-connect/logout";

    public static final String INTROSPECT_API = "http://localhost:8180/realms/userdev/protocol/openid-connect/token/introspect";
    public static final String LOGGED_OUT = "Logged Out Successfully";
    public static final String CLIENT_ID_PROP = "${spring.security.oauth2.client.registration.oauth2-client-credentials.client-id}";
    public static final String CLIENT_SECRET_PROP = "${spring.security.oauth2.client.registration.oauth2-client-credentials.client-secret}";
    public static final String GRANT_TYPE_PROP = "${spring.security.oauth2.client.registration.oauth2-client-credentials.authorization-grant-type}";
}
